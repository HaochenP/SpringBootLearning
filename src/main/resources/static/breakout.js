const canvas = document.getElementById("myCanvas");
const ctx = canvas.getContext("2d");

let ballRadius = 10;
let x = canvas.width / 2;
let y = canvas.height - 30;
let dx = 2;
let dy = -2;
let paddleHeight = 10;
let paddleWidth = 75;
let paddleX = (canvas.width - paddleWidth) / 2;
let rightPressed = false;
let leftPressed = false;
let brickRowCount = 5;
let brickColumnCount = 3;
let brickWidth = 75;
let brickHeight = 20;
let brickPadding = 10;
let brickOffsetTop = 30;
let brickOffsetLeft = 30;
let score = 0;
let lives = 1;
let actorsToDelete = [];
let isGameRunning = true;
const bricks = [];
for (c = 0; c < brickColumnCount; c++) {
    bricks[c] = [];
    for (r = 0; r < brickRowCount; r++) {
        bricks[c][r] = { x: 0, y: 0, status: 1 };
    }
}

function resetVariables() {
    lives = 1;
    score = 0;
    actorsToDelete = [];

    x = canvas.width / 2;
    y = canvas.height - 30;

    dx = 2;
    dy = -2;

    paddleX = (canvas.width - paddleWidth) / 2;

    for(let c = 0; c < brickColumnCount; c++) {
        for(let r = 0; r < brickRowCount; r++) {
            bricks[c][r].status = 1;
        }
    }
}

async function fetchActors() {
    const response = await fetch('/allActors'); 
    const actors = await response.json();
    console.log(actors);
    brickRowCount = actors.length < 5 ? actors.length : 5; 
    for (let c = 0; c < brickColumnCount; c++) {
        bricks[c] = [];
        for (let r = 0; r < brickRowCount; r++) {
            let actor = actors[r + c * brickRowCount]; 
            if (actor) {
                bricks[c][r] = { x: 0, y: 0, status: 1, id: actor.id, name: actor.firstname }; 
            }
        }
    }
}


document.addEventListener("keydown", keyDownHandler, false);
document.addEventListener("keyup", keyUpHandler, false);
document.addEventListener("mousemove", mouseMoveHandler, false);

function keyDownHandler(e) {
    if (e.keyCode == 39) {
        rightPressed = true;
    }
    else if (e.keyCode == 37) {
        leftPressed = true;
    }
}
function keyUpHandler(e) {
    if (e.keyCode == 39) {
        rightPressed = false;
    }
    else if (e.keyCode == 37) {
        leftPressed = false;
    }
}
function mouseMoveHandler(e) {
    const relativeX = e.clientX - canvas.offsetLeft;
    if (relativeX > 0 && relativeX < canvas.width) {
        paddleX = relativeX - paddleWidth / 2;
    }
}
function collisionDetection() {
    for (c = 0; c < brickColumnCount; c++) {
        for (r = 0; r < brickRowCount; r++) {
            const b = bricks[c][r];
            if (b.status == 1) {
                if (x > b.x && x < b.x + brickWidth && y > b.y && y < b.y + brickHeight) {
                    dy = -dy;
                    b.status = 0;
                    score++;
                    actorsToDelete.push(b.id);
                    if (score == brickRowCount * brickColumnCount) {
                        alert("YOU WIN, CONGRATS!");
                        askForDeletionConfirmation();
                    }
                }
            }
        }
    }
}

function drawBall() {
    ctx.beginPath();
    ctx.arc(x, y, ballRadius, 0, Math.PI * 2);
    ctx.fillStyle = "#0095DD";
    ctx.fill();
    ctx.closePath();
}
function drawPaddle() {
    ctx.beginPath();
    ctx.rect(paddleX, canvas.height - paddleHeight, paddleWidth, paddleHeight);
    ctx.fillStyle = "#0095DD";
    ctx.fill();
    ctx.closePath();
}
function drawBricks() {
    for (let c = 0; c < brickColumnCount; c++) {
        for (let r = 0; r < brickRowCount; r++) {
            if (bricks[c][r].status == 1) {
                const brickX = (r * (brickWidth + brickPadding)) + brickOffsetLeft;
                const brickY = (c * (brickHeight + brickPadding)) + brickOffsetTop;
                bricks[c][r].x = brickX;
                bricks[c][r].y = brickY;

                ctx.beginPath();
                ctx.rect(brickX, brickY, brickWidth, brickHeight);
                ctx.fillStyle = "#0095DD"; 
                ctx.fill();
                ctx.closePath();

                ctx.fillStyle = "#FFFFFF"; 
                ctx.font = "16px Arial";
                const textWidth = ctx.measureText(bricks[c][r].name).width;
                const textX = brickX + (brickWidth - textWidth) / 2;
                const textY = brickY + (brickHeight + 8) / 2; 
                ctx.fillText(bricks[c][r].name, textX, textY);
            }
        }
    }
}

function drawScore() {
    ctx.font = "16px Arial";
    ctx.fillStyle = "#0095DD";
    ctx.fillText("Score: " + score, 8, 20);
}
function drawLives() {
    ctx.font = "16px Arial";
    ctx.fillStyle = "#0095DD";
    ctx.fillText("Lives: " + lives, canvas.width - 65, 20);
}

function draw() {
    if(!isGameRunning){
        return;
    }
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    drawBricks();
    drawBall();
    drawPaddle();
    drawScore();
    drawLives();
    collisionDetection();

    if (x + dx > canvas.width - ballRadius || x + dx < ballRadius) {
        dx = -dx;
    }
    if (y + dy < ballRadius) {
        dy = -dy;
    }
    else if (y + dy > canvas.height - ballRadius) {
        if (x > paddleX && x < paddleX + paddleWidth) {
            dy = -dy;
        }
        else {
            lives--;
            if (!lives) {
                alert("GAME OVER");
                askForDeletionConfirmation();
            }
            else {
                x = canvas.width / 2;
                y = canvas.height - 30;
                dx = 3;
                dy = -3;
                paddleX = (canvas.width - paddleWidth) / 2;
            }
        }
    }

    if (rightPressed && paddleX < canvas.width - paddleWidth) {
        paddleX += 7;
    }
    else if (leftPressed && paddleX > 0) {
        paddleX -= 7;
    }

    x += dx;
    y += dy;
    requestAnimationFrame(draw);
}

function askForDeletionConfirmation() {
    alert(actorsToDelete)
    if (confirm("Do you want to delete all hit actors?")) {
        deleteActorsByIds(actorsToDelete);

    }
    $('#breakout-game').hide();
    isGameRunning = false;
    resetVariables();
}

async function deleteActorsByIds(ids) {
    return await $.ajax({
        type: "POST",
        url: "/deleteActorsByIds",
        data: JSON.stringify(ids),
        contentType: "application/json"
    });
}

//draw();