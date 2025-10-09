package dev.augustobarbosa.magic8ball.magic8ball

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.augustobarbosa.magic8ball.magic8ball.ui.theme.Magic8BallTheme
import kotlin.random.Random
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

open class SmartDevice protected constructor(val name: String, val category: String) {
    var deviceStatus = "online"
        protected set(value) {
            field = value
        }

    open val deviceType = "unknown"

    open fun turnOn() {
        deviceStatus = "on"
    }

    open fun turnOff() {
        deviceStatus = "off"
    }
}



class RangeRegulator(
    initialValue: Int,
    private val minValue: Int,
    private val maxValue: Int
) : ReadWriteProperty<Any?, Int> {

    var fieldData = initialValue

    override fun getValue(thisRef: Any?, property: KProperty<*>): Int {
        return fieldData
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        if (value in minValue..maxValue) {
            fieldData = value
        }
    }
}




class SmartTvDevice(deviceName: String, deviceCategory: String) :
    SmartDevice(name = deviceName, category = deviceCategory) {

    override val deviceType = "Smart TV"

    private var speakerVolume by RangeRegulator(initialValue = 2, minValue = 0, maxValue = 100)
//    private var speakerVolume = 2
//        set(value) {
//            if (value in 0..100) {
//                field = value
//            }
//        }

    private var channelNumber by RangeRegulator(initialValue = 1, minValue = 0, maxValue = 200)
//    private var channelNumber = 1
//        set(value) {
//            if (value in 0..200) {
//                field = value
//            }
//        }

    fun increaseSpeakerVolume() {
        speakerVolume++
        println("Speaker volume increased to $speakerVolume.")
    }

    protected fun nextChannel() {
        channelNumber++
        println("Channel number increased to $channelNumber.")
    }

    override fun turnOn() {
        super.turnOn()
        println(
            "$name is turned on. Speaker volume is set to $speakerVolume and channel number is " +
                    "set to $channelNumber."
        )
    }
    override fun turnOff() {
        super.turnOff()
        println("$name turned off")
    }
}


class SmartLightDevice(deviceName: String, deviceCategory: String) :
    SmartDevice(name = deviceName, category = deviceCategory) {

    override val deviceType = "Smart Light"

    private var brightnessLevel by RangeRegulator(initialValue = 0, minValue = 0, maxValue = 100)
//    private var brightnessLevel = 0
//        set(value) {
//            if (value in 0..100) {
//                field = value
//            }
//        }

    fun increaseBrightness() {
        brightnessLevel++
        println("Brightness increased to $brightnessLevel.")
    }

    override fun turnOn() {
        super.turnOn()
        brightnessLevel = 2
        println("$name turned on. The brightness level is $brightnessLevel.")
    }
    override fun turnOff() {
        super.turnOff()
        brightnessLevel = 0
        println("Smart Light turned off")
    }

}

// COMPOSIÇÃO
class SmartHome(
    val smartTvDevice: SmartTvDevice,
    val smartLightDevice: SmartLightDevice
) {

    var deviceTurnOnCount = 0
        private set


    fun turnOnTv() {
        deviceTurnOnCount++
        smartTvDevice.turnOn()
    }
    fun turnOffTv() {
        deviceTurnOnCount--
        smartTvDevice.turnOff()
    }
    fun increaseTvVolume() {
        smartTvDevice.increaseSpeakerVolume()
    }
    fun changeTvChannelToNext() {
        smartTvDevice.nextChannel()
    }

    fun turnOnLight() {
        deviceTurnOnCount++
        smartLightDevice.turnOn()
    }
    fun turnOffLight() {
        deviceTurnOnCount--
        smartLightDevice.turnOff()
    }
    fun increaseLightBrightness() {
        smartLightDevice.increaseBrightness()
    }

    fun turnOffAllDevices() {
        turnOffTv()
        turnOffLight()
    }
}



fun main() {
    var smartDevice: SmartDevice = SmartTvDevice("Android TV", "Entertainment")
    smartDevice.turnOn()

    smartDevice = SmartLightDevice("Google Light", "Utility")
    smartDevice.turnOn()
}



/*
Entendendo a ReadWriteProperty

A interface ReadWriteProperty<Any?, Int> é a peça central do mecanismo de delegação de propriedades. Ela define o contrato para classes que agem como "delegados" para propriedades que podem ser lidas e escritas.

    <Any?, Int>: Esses são os tipos genéricos da interface.

        Any?: O primeiro tipo (T) é o tipo do objeto que possui a propriedade delegada. O Any? (aceitando qualquer tipo de objeto, incluindo null) é um tipo genérico que torna o delegado flexível, permitindo que ele seja usado por qualquer classe, como a SmartTvDevice em seu código.

        Int: O segundo tipo (V) é o tipo do valor da propriedade delegada. No seu caso, RangeRegulator está delegando propriedades do tipo Int (como speakerVolume e channelNumber), então o valor é um inteiro.

Qualquer classe que implemente ReadWriteProperty deve fornecer as funções getValue e setValue.
 */

/*
O que são thisRef: Any? e property: KProperty<*>?

Esses parâmetros são passados para as funções getValue e setValue pelo Kotlin para fornecer informações sobre a propriedade que está sendo acessada ou modificada.

    thisRef: Any?: O parâmetro thisRef se refere à instância da classe que contém a propriedade delegada. No seu código, quando você acessa speakerVolume ou channelNumber, o thisRef será a instância de SmartTvDevice. Embora não seja usado dentro da sua classe RangeRegulator, ele é útil em cenários mais complexos onde o comportamento do delegado precisa saber qual objeto o está usando.

    property: KProperty<*>: O parâmetro property é um objeto de reflexão que representa a propriedade que está sendo delegada. Ele permite que você acesse metadados da propriedade, como seu nome. O KProperty<*> é a forma genérica de se referir a uma propriedade (* é o curinga, indicando que o tipo da propriedade pode ser qualquer um). Em getValue e setValue, o property seria uma representação de speakerVolume ou channelNumber, permitindo que você obtenha informações como o nome da propriedade (property.name).

A delegação de propriedades é uma forma de seguir o princípio "Don't Repeat Yourself" (DRY). Em vez de escrever a mesma lógica de validação de 0..100 e 0..200 em cada setter, você cria uma classe (RangeRegulator) que encapsula essa lógica. O Kotlin então usa o by para "delegar" a responsabilidade de gerenciar o acesso (get e set) a essa classe.
 */