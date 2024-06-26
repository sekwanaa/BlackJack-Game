<div align="center">
  
  # CLI BlackJack Game
  
</div>

Welcome to the CLI Blackjack Game, a command-line interface implementation of the classic casino game Blackjack. This project allows you to play Blackjack against the computer in your terminal.

### Table of Contents
* [Features](https://github.com/sekwanaa/BlackJack-Game?tab=readme-ov-file#features)
* [Installation](https://github.com/sekwanaa/BlackJack-Game?tab=readme-ov-file#installation)
* [Usage](https://github.com/sekwanaa/BlackJack-Game?tab=readme-ov-file#usage)
* [Rules](https://github.com/sekwanaa/BlackJack-Game?tab=readme-ov-file#rules)
* [Contributing](https://github.com/sekwanaa/BlackJack-Game?tab=readme-ov-file#contributing)

### Features
* Play classic Blackjack against the computer.
* Simple and intuitive command-line interface.

### Installation
#### Prerequisites
* Java Development Kit (JDK) 17 or higher

### Steps
1. Clone the repository:
``` bash
git clone https://github.com/yourusername/BlackJack-Game.git
```
2. Navigate to the project directory:
``` bash
cd BlackJack-Game
```
3. Compile the Java files:
``` bash
javac -d bin src/com/yourusername/blackjack/*.java
```

### Usage
Run the game using the following command:
``` bash
java -cp bin com.yourusername.blackjack.Blackjack
```

### Commands
* **'hit'** - Draw another card.
* **'stay'** - End your turn.
* **'insurance'** - Take insurance if the dealer shows an Ace.

  <details>
  
  **<summary>To do</summary>**
  
  * **'double'** - Double your bet, draw one more card, and end your turn.
  * **'split'** - Split your hand into two hands (if you have a pair).
      
  </details>

### Rules
The objective of Blackjack is to beat the dealer by having a hand value closer to 21 without exceeding it.\
Here are some key rules:

* **Card Values**: Number cards are worth their face value, face cards (King, Queen, Jack) are worth 10, and Aces can be worth 1 or 11.
* **Gameplay**:
  * Each player is dealt two cards, face up. The dealer is dealt two cards, with one face up and one face down.
  * Players must decide to hit, stand, double, or split.
  * The dealer reveals the hidden card and must hit until their cards total 17 or higher.
* **Winning**:
  * If your hand is closer to 21 than the dealer’s hand, you win.
  * If your hand exceeds 21, you bust and lose the bet.
  * If the dealer busts, you win.
  * If you and the dealer have the same total, it's a push (tie).

For a complete set of rules, please refer to the [BlackJack Rules](https://bicyclecards.com/how-to-play/blackjack/).

### Contributing
We welcome contributions to this project!\
To contribute:

1. Fork the repository.
2. Create a new branch:
``` bash
git checkout -b feature-name
```
3. Make your changes and commit them:
``` bash
git commit -m "Description of your feature"
```
4. Push to the branch:
``` bash
git push origin feature-name
```
5. Open a pull request detailing your changes.

Please ensure your code follows our [Code of Conduct](https://github.com/sekwanaa/BlackJack-Game/blob/main/CODE_OF_CONDUCT.md) and adheres to the [contribution guidelines](https://github.com/sekwanaa/BlackJack-Game/blob/main/CONTRIBUTING.md).
<details>
  
  **<summary>Screenshots</summary>**

### Home Screen
![image](https://github.com/sekwanaa/BlackJack-Game/assets/112197395/e93e629c-d81b-4539-8f9a-691ab13f9c74)

### Playing the game
![image](https://github.com/sekwanaa/BlackJack-Game/assets/112197395/483deefc-ce73-4244-bbd9-f8bcffdb0f9a)

![image](https://github.com/sekwanaa/BlackJack-Game/assets/112197395/67c74ed6-f957-4393-be74-aca1153f9eaf)

### Getting BlackJack (Exactly 21)
![image](https://github.com/sekwanaa/BlackJack-Game/assets/112197395/61d8f405-953b-4a73-a7cc-8a255d78bc8e)

### Busted (Going over 21)
![image](https://github.com/sekwanaa/BlackJack-Game/assets/112197395/5ae1be45-c718-412d-bc36-13df386d81da)

### Winning
![image](https://github.com/sekwanaa/BlackJack-Game/assets/112197395/4f1c5c91-15a8-4b0b-a709-42b40ef58c23)

  
</details>
