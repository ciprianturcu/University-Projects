// This Arduino sketch offers interactive Morse code learning through both encoding and decoding functionalities. Users can input text via the Serial Monitor for encoding or use a button to input Morse code for decoding.
// The program supports letters and numbers, displaying the corresponding Morse code on the Serial Monitor along with visual (LED) and auditory (tone) feedback. Unrecognized inputs are indicated with a "?".
//
// Usage Instructions:
// - For Morse code learning, input a word in the Serial Monitor. Then, attempt to replicate the Morse code using the button.
// - Maintain short durations for dots and longer durations for dashes.
// - Implement brief pauses after letters and longer pauses after words for clarity.
// - Aim for precise button presses and releases for accuracy.
//
// Circuit Configuration:
// - Connect a piezo buzzer or speaker to pin 2 and ground.
// - Connect a button between pin 8 and ground.
// - Connect an LED's positive terminal to pin 13 and its negative terminal through a 220 Ohm resistor to ground.

int tonePin = 2;
int toneFreq = 1000;
int ledPin = 13;
int buttonPin = 8;
int debounceDelay = 30;

int dotLength = 240; 
// dotLength = basic unit of speed in milliseconds
// 240 gives 5 words per minute (WPM) speed.
// WPM = 1200/dotLength.
// For other speeds, use dotLength = 1200/(WPM)
//
// Other lengths are computed from dot length
  int dotSpace = dotLength;
  int dashLength = dotLength*3;
  int letterSpace = dotLength*3;
  int wordSpace = dotLength*7; 
  float wpm = 1200./dotLength;
  
int t1, t2, onTime, gap;
bool newLetter, newWord, letterFound, keyboardText;
int lineLength = 0;
int maxLineLength = 20; 

char* letters[] = 
{
".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", // A-I
".---", "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", // J-R 
"...", "-", "..-", "...-", ".--", "-..-", "-.--", "--.." // S-Z
};

char* numbers[] = 
{
"-----", ".----", "..---", "...--", "....-", //0-4
".....", "-....", "--...", "---..", "----." //5-9
};

String dashSeq = "";
char keyLetter, ch;
int i, index;

void setup() {
  delay(500);
  pinMode(ledPin, OUTPUT);
  pinMode(tonePin, OUTPUT);
  pinMode(buttonPin, INPUT_PULLUP);
  Serial.begin(9600);
  Serial.println("\n-------------------------------\nMorse Code decoder/encoder");
  Serial.print("Speed=");
  Serial.print(wpm);
  Serial.print("wpm, dot=");
  Serial.print(dotLength);
  Serial.println("ms");
  
  // Test the LED and tone
  testLEDandTone();

  // Flash sequences for demonstration
  flashDemoSequences();

  Serial.println("\n-------------------------------\nClick field in Serial Monitor,\ntype text and press Enter, or\nKey in Morse Code to decode:\n-------------------------------");
  newLetter = false; //if false, do NOT check for end of letter gap
  newWord = false;  //if false, do NOT check for end of word gap
  keyboardText = false;
}

void testLEDandTone() {
  // Turn on the LED and tone
  digitalWrite(ledPin, HIGH);
  tone(tonePin, toneFreq);
  delay(2000); // Keep them on for 2 seconds

  // Turn off the LED and tone
  digitalWrite(ledPin, LOW);
  noTone(tonePin);
  delay(600); // Wait for 600 ms before continuing
}

void flashDemoSequences() {
  // Flash Morse code for "A"
  Serial.print("A .-  ");
  flashSequence(letters['A' - 'A']); // letters[0] corresponds to "A"
  delay(wordSpace); // Wait for a word space before the next letter

  // Flash Morse code for "B"
  Serial.print("B -...  ");
  flashSequence(letters['B' - 'A']); // letters[1] corresponds to "B"
  delay(wordSpace); // Wait for a word space before the next letter

  // Flash Morse code for "C"
  Serial.print("C -.-.  ");
  flashSequence(letters['C' - 'A']); // letters[2] corresponds to "C"
  delay(wordSpace); // End with a word space

  Serial.println(); // Move to a new line after demonstrating the sequences
}



void loop() {
  handleKeyboardInput();
  handleButtonInput();
  manageTimingAndDisplay();
}

void handleKeyboardInput() {
  if (Serial.available() > 0) {
    manageKeyboardTextHeader();

    ch = Serial.read();
    ch = convertToUpperCase(ch);
    
    encodeAndFlashMorse(ch);

    if (Serial.available() <= 0) {
      printKeyboardInputHeader();
    }
  }
}

char convertToUpperCase(char character) {
  if (character >= 'a' && character <= 'z') {
    return character - 32;
  }
  return character;
}

void encodeAndFlashMorse(char character) {
  if (character >= 'A' && character <= 'Z') {
    Serial.print(character);
    Serial.print(" ");
    Serial.println(letters[character - 'A']);
    flashSequence(letters[character - 'A']);
    delay(letterSpace);
  } else if (character >= '0' && character <= '9') {
    Serial.print(character);
    Serial.print(" ");
    Serial.println(numbers[character - '0']);
    flashSequence(numbers[character - '0']);
    delay(letterSpace);
  } else if (character == ' ') {
    Serial.println("_");
    delay(wordSpace);
  }
}

void manageKeyboardTextHeader() {
  if (!keyboardText) {
    Serial.println("\n-------------------------------");
  }
  keyboardText = true;
}

void printKeyboardInputHeader() {
  Serial.println("\nEnter text or Key in:\n-------------------------------");
  keyboardText = false;
}

void handleButtonInput() {
  if (digitalRead(buttonPin) == LOW) {
    markStartTime();
    waitForButtonRelease();
    classifyInput();
  }
}

void markStartTime() {
  newLetter = true;
  newWord = true;
  t1 = millis();
  digitalWrite(ledPin, HIGH);
  tone(tonePin, toneFreq);
  delay(debounceDelay);
}

void waitForButtonRelease() {
  while (digitalRead(buttonPin) == LOW) {
    delay(debounceDelay);
  }
  delay(debounceDelay);
  t2 = millis();
  onTime = t2 - t1;
  digitalWrite(ledPin, LOW);
  noTone(tonePin);
}

void classifyInput() {
  if (onTime <= dotLength * 1.5) {
    dashSeq += ".";
  } else {
    dashSeq += "-";
  }
}

void manageTimingAndDisplay() {
  gap = millis() - t2;
  if (newLetter && gap >= letterSpace) {
    decodeSequence();
    newLetter = false;
    dashSeq = "";
    manageLineLength();
  }
  if (newWord && gap >= wordSpace * 1.5) {
    insertWordSpace();
  }
}

void decodeSequence() {
  letterFound = false;
  keyLetter = '?';
  for (int i = 0; i <= 25; i++) {
    if (dashSeq == letters[i]) {
      keyLetter = i + 65;
      letterFound = true;
      break;
    }
  }
  if (!letterFound) {
    for (int i = 0; i <= 9; i++) {
      if (dashSeq == numbers[i]) {
        keyLetter = i + 48;
        letterFound = true;
        break;
      }
    }
  }
  Serial.print(keyLetter);
  if (!letterFound) {
    tone(tonePin, 100, 500); // buzz for unknown sequence
  }
}

void manageLineLength() {
  lineLength += 1;
  if (lineLength >= maxLineLength) {
    Serial.println();
    lineLength = 0;
  }
}

void insertWordSpace() {
  newWord = false;
  Serial.print("_");
  lineLength += 1;
  flashIndicator();
}

void flashIndicator() {
  digitalWrite(ledPin, HIGH);
  delay(25);
  digitalWrite(ledPin, LOW);
}

void flashSequence(char* sequence) {
  int i = 0;
  while (sequence[i] != '\0') { // Continue until the end of the string
    flashDotOrDash(sequence[i]);
    i++;
  }
}

void flashDotOrDash(char dotOrDash) {
  digitalWrite(ledPin, HIGH); // Turn on LED and tone
  tone(tonePin, toneFreq);
  
  // Determine the duration based on whether it's a dot or a dash
  if (dotOrDash == '.') {
    delay(dotLength); // Short delay for dot
  } else {
    delay(dashLength); // Longer delay for dash
  }

  digitalWrite(ledPin, LOW); // Turn off LED and tone
  noTone(tonePin);
  delay(dotLength); // Inter-element gap within a character
}
