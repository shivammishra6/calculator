package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.ui.theme.CalculatorTheme
import net.objecthunter.exp4j.ExpressionBuilder


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Calculator(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}


@Composable
fun Calculator(modifier: Modifier = Modifier) {
    var displayText by remember { mutableStateOf("") }
    val small = dimensionResource(id = R.dimen.small)
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        TextField(
            value = displayText,
            onValueChange = { displayText = it },
            readOnly = true,
            singleLine = false,
            textStyle = TextStyle(
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .fillMaxSize()
                .padding(28.dp)
                .weight(1.4f)
        )

        val buttons = listOf(
            listOf("C", "%", "X", "/"),
            listOf("7", "8", "9", "*"),
            listOf("4", "5", "6", "-"),
            listOf("1", "2", "3", "+"),
            listOf("00", "0", ".", "=")
        )


        Column(Modifier.weight(2f)) {
            buttons.forEach { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    row.forEach { buttonText ->
                        CalculatorButton(buttonText) {
                            displayText = when (buttonText) {
                                "=" -> {
                                    // Evaluate the expression
                                    evaluate(displayText)
                                }

                                "C" -> ""
                                "X" -> displayText.dropLast(1)
                                else -> {
                                    addCharacterToInput(displayText, buttonText)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun addCharacterToInput(currentInput: String, newChar: String): String {
    val operators = setOf('.','+', '-', '*', '/', '%')
    val sOperators = setOf(".","+", "-", "*", "/", "%")
    return if (currentInput.isNotEmpty() && currentInput.last() in operators && newChar in sOperators) {
        // Ignore the new operator
        currentInput
    } else {
        // Add the new character
        if (currentInput.isEmpty() && newChar in sOperators)
            currentInput
        else
            currentInput + newChar
    }
}

fun evaluate(displayText: String): String {
    if (displayText.isEmpty())
        return displayText
    val char = displayText.last()
    val string: String =
        if (char == '*' || char == '-' || char == '+' || char == '/' || char == '%')
            displayText.dropLast(1)
        else
            displayText
    val e = ExpressionBuilder(string).build()
    val result = e.evaluate()
    return if (result % 1 == 0.0) {
        result.toLong().toString()
    } else {
        result.toString()
    }
}

@Composable
fun CalculatorButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(76.dp)
            .width(76.dp),
        shape = CircleShape
    ) {
        Text(
            text = text,
            fontSize = 24.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CalculatorTheme {
        Calculator()
    }
}