let actorId = 0;
let numberOfGuesses = 0;
let highestId = 0;
let gameStarted = false;
getHighestId();


function guessNumber(direction){
    currentNumber = parseInt(document.getElementById("actorId").innerHTML);
    let newGuess = 0;
    if (direction == "higher"){
        newGuess = Math.floor(Math.random() * (highestId - currentNumber) + currentNumber);
    }
    else if (direction == "lower"){
        newGuess = Math.floor(Math.random() * currentNumber);
    }
    actorId = newGuess;
    document.getElementById("actorId").innerHTML = newGuess;

}



async function getHighestId(){
    let response = await fetch("lastActor");
    let data = await response.json();
    console.log(data.id)
    highestId  = data.id;
}


async function getActor(){
    let response = await fetch("actorById/" + actorId);
    let data = await response.json();
    alert(data.firstname + " " + data.lastName);
}

function searchActor(){
    if (!gameStarted) {
        gameStarted = true;
        alert("Game is starting! Win the game to search for an actor.");
        $('#actorSearch').modal('hide');
        startBlackJack(); 
        return; 
    }
    if(gameStarted && !hasPlayerWon){
        alert("Game is starting! Win the game to search for an actor.");
        $('#actorSearch').modal('hide');
        startBlackJack(); 
        return; }
    if (!hasPlayerWon) {
        alert("You must win the blackjack game before searching for an actor!");
        return; 
    }

    actorId = parseInt(document.getElementById("actorId").innerHTML); 
    getActor();
}







let hasPlayerWon = false;

let dealerSum = 0;
let yourSum = 0;

let dealerAceCount = 0;
let yourAceCount = 0; 

let hidden;
let deck;

let canHit = true; //allows the player (you) to draw while yourSum <= 21

function startBlackJack() {
    resetGameVariables(); 
    //clearUI(); 
    $('#verification').show();
    buildDeck();
    shuffleDeck();
    startGame();
}

function buildDeck() {
    let values = ["A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"];
    let types = ["C", "D", "H", "S"];
    deck = [];

    for (let i = 0; i < types.length; i++) {
        for (let j = 0; j < values.length; j++) {
            deck.push(values[j] + "-" + types[i]); //A-C -> K-C, A-D -> K-D
        }
    }
    // console.log(deck);
}

function shuffleDeck() {
    for (let i = 0; i < deck.length; i++) {
        let j = Math.floor(Math.random() * deck.length); // (0-1) * 52 => (0-51.9999)
        let temp = deck[i];
        deck[i] = deck[j];
        deck[j] = temp;
    }
    console.log(deck);
}

function startGame() {
    hidden = deck.pop();
    dealerSum += getValue(hidden);
    dealerAceCount += checkAce(hidden);
    // console.log(hidden);
    // console.log(dealerSum);
    while (dealerSum < 17) {
        //<img src="./cards/4-C.png">
        let cardImg = document.createElement("img");
        let card = deck.pop();
        cardImg.src = "./cards/" + card + ".png";
        dealerSum += getValue(card);
        dealerAceCount += checkAce(card);
        document.getElementById("dealer-cards").append(cardImg);
    }
    console.log(dealerSum);

    for (let i = 0; i < 2; i++) {
        let cardImg = document.createElement("img");
        let card = deck.pop();
        cardImg.src = "./cards/" + card + ".png";
        yourSum += getValue(card);
        yourAceCount += checkAce(card);
        document.getElementById("your-cards").append(cardImg);
    }

    console.log(yourSum);
    document.getElementById("hit").addEventListener("click", hit);
    document.getElementById("stay").addEventListener("click", stay);

}

function hit() {
    if (!canHit) {
        return;
    }

    let cardImg = document.createElement("img");
    let card = deck.pop();
    cardImg.src = "./cards/" + card + ".png";
    yourSum += getValue(card);
    yourAceCount += checkAce(card);
    document.getElementById("your-cards").append(cardImg);

    if (reduceAce(yourSum, yourAceCount) > 21) { //A, J, 8 -> 1 + 10 + 8
        canHit = false;
    }

}

function stay() {
    dealerSum = reduceAce(dealerSum, dealerAceCount);
    yourSum = reduceAce(yourSum, yourAceCount);

    canHit = false;
    document.getElementById("hidden").src = "./cards/" + hidden + ".png";

    let message = "";
    if (yourSum > 21) {
        message = "You Lose!";
        hasPlayerWon = false; // Player loses
        alert("You lost! You are not human")
        $('#verification').hide();
        $('#searchButton').prop('disabled', true);
    
    }
    else if (dealerSum > 21 || yourSum > dealerSum) {
        message = "You Win!";
        hasPlayerWon = true; // Player wins
        alert("You won! You can now search for an actor.")
        $('#actorSearch').modal('show');
        $('#verification').hide();

    }
    else {
        message = "You Lose!";
        hasPlayerWon = false; // Player loses
        alert("You lost! You are not human")
        $('#verification').hide();
        $('#searchButton').prop('disabled', true);
    }
    document.getElementById("dealer-sum").innerText = dealerSum;
    document.getElementById("your-sum").innerText = yourSum;
    document.getElementById("results").innerText = message;
}

function getValue(card) {
    let data = card.split("-"); // "4-C" -> ["4", "C"]
    let value = data[0];

    if (isNaN(value)) { //A J Q K
        if (value == "A") {
            return 11;
        }
        return 10;
    }
    return parseInt(value);
}

function checkAce(card) {
    if (card[0] == "A") {
        return 1;
    }
    return 0;
}

function reduceAce(playerSum, playerAceCount) {
    while (playerSum > 21 && playerAceCount > 0) {
        playerSum -= 10;
        playerAceCount -= 1;
    }
    return playerSum;
}

function resetGameVariables() {
    dealerSum = 0;
    yourSum = 0;
    dealerAceCount = 0;
    yourAceCount = 0;
    canHit = true;
    hasPlayerWon = false; 
    gameStarted = true; 
    deck = []; 
}

function clearUI() {
    document.getElementById("dealer-cards").innerHTML = '';
    document.getElementById("your-cards").innerHTML = '';
    document.getElementById("results").innerText = '';
    document.getElementById("dealer-sum").innerText = '';
    document.getElementById("your-sum").innerText = '';

}
