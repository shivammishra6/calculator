package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DimenRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.calculator.ui.theme.CalculatorTheme


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

    val fontSize = dimensionResource(id = R.dimen.display)
    val small = dimensionResource(id = R.dimen.small)
    val medium = dimensionResource(id = R.dimen.medium)

    Column(modifier = modifier.fillMaxSize()) {

        Row(
            modifier = Modifier
                .weight(1f)
                .padding(top = medium)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                TextField(
                    value = "",
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .fillMaxWidth()
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(2f)
                .padding(bottom = dimensionResource(id = R.dimen.verySmall))
        ) {
            CalculatorRow(
                R.string.ac,
                R.string.per,
                R.string.clear,
                R.string.divide,
                Modifier.weight(1f)
            )
            CalculatorRow(
                R.string.seven,
                R.string.eight,
                R.string.nine,
                R.string.multiply,
                Modifier.weight(1f)
            )
            CalculatorRow(
                R.string.four,
                R.string.five,
                R.string.six,
                R.string.subtract,
                Modifier.weight(1f)
            )
            CalculatorRow(
                R.string.one,
                R.string.two,
                R.string.three,
                R.string.plus,
                Modifier.weight(1f)
            )
            CalculatorRow(
                R.string.doubleZero,
                R.string.zero,
                R.string.dot,
                R.string.equals,
                Modifier.weight(1f)
            )
        }

    }
}

@Composable
fun CalculatorRow(
    @StringRes one:Int,
    @StringRes two:Int,
    @StringRes three:Int,
    @StringRes four:Int,
    modifier:Modifier=Modifier
) {
    Row (modifier = modifier){
        CalculatorColumn(one,Modifier.weight(1f))
        CalculatorColumn(two, Modifier.weight(1f))
        CalculatorColumn(three, Modifier.weight(1f))
        CalculatorColumn(four, Modifier.weight(1f))
    }
}

@Composable
fun CalculatorColumn(
    @StringRes value:Int,
    modifier: Modifier=Modifier,
    @DimenRes size:Int=R.dimen.buttonText
) {
    Button(
        onClick = { /*TODO*/ },
        shape = RoundedCornerShape(50),
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.verySmall))
            .height(dimensionResource(id = R.dimen.large))
    ) {
        Text(
            text = stringResource(id = value),
            fontSize = dimensionResource(id = size).value.sp
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