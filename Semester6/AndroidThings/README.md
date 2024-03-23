## Overview
I have created an morse code encoder/decoder system. The input of the program consits of short or long presses of the button. The short press represents a dot and the long press represents a dash. Here is the morse code alphabet: 
![image](https://github.com/ciprianturcu/University-Projects/assets/102388372/be88e313-2f57-4619-8851-de08694027ba)
The system can be used in decoding or encoding messages that can be feed in by either the button or the serial monitor. The supported alphabet is the 26 letters of the english alphabet and numbers from 0-9
## Schema

![image](https://github.com/ciprianturcu/University-Projects/assets/102388372/5e1e26b2-7848-4e33-a32f-fd3011c24786)
![image](https://github.com/ciprianturcu/University-Projects/assets/102388372/56f0fb6c-86a9-4ed0-8b6d-b8eb8b5c9c90)


## Pre-requisites

- USB CABLE A-B (https://cleste.ro/cablu-usb-a-b-05m.html)
- ARDUINO UNO R3 ATMEGA328P (https://cleste.ro/arduino-uno-r3-atmega328p.html)
- BUZZER MODULE (https://cleste.ro/modul-buzzer-pasiv.html)
- BUTTON (https://cleste.ro/butoane-tactile-6x6x5mm.html)
- LED (https://cleste.ro/led-de-5-mm.html)
- BREADBOARD (https://cleste.ro/breadboard-400-puncte.html)
- 220 Ohm REZISTOR (https://cleste.ro/set-rezistene-100buc-e4-3.html)
- Male-Male Dupont Wires (https://cleste.ro/10-x-fire-dupont-tata-tata-10cm.html)

## Setup and Build Plan
1.First we need to install the Arduino IDE in order to send the system a program to run.
2.We connect the button, led and buzzer to the breadborad.
3.We than set up the connections between the arduino board pins and each of the components on the breadboard (we need a ground connection between the arduino and breadboard such that afterwards each component on the breadboard can connect to this ground connection; and after that for each component we have a connection to an individual arduino pin)
4.We then connect the Arduino to the computer through the power cable having the IDE started.
5.We upload the code to the arduino
  
## Running
1. The code should run auttomaticaly on power up of the system
2. Enter a sequence of short/long(dots/dashes) such that they resemble a correct letter according to the alphabet.
3. See the output within the serial monitor
