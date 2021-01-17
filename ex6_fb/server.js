/* This class is an implementation of a Node.js express
server which gets a stock symbol as input from the user,
builds an API request and gets the stocks price out of the
request data returned*/

const express = require('express');
const bodyParser = require('body-parser');
const FCM = require('fcm-push');
const fetch = require("node-fetch");

//API URL
const API_URL = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol="
//API KEY
const API_KEY = "F5JB69QFMFFNAPYR";
//CLOUD MESSAGING SERVER KEY
const SERVER_KEY = "AAAAA_gUjJU:APA91bEpfZcshUQLachHDpU_A9761jhKo-TEazpjMpFYsdoPYXzjs2_Pkw9fCYR7caohidWwcgdXEmxekvQ2Fcen6JdlIRac3QcWYfo554d1xtzL4vZEy7ckw8S-dLJOCu-GY68MQRgq";
const PORT = 8080;

let app = express();
app.use(bodyParser.json());
let fcm = new FCM(SERVER_KEY);
let token = "";

//Getting the users token
app.post('/token/:token', (req, res) => {
    token = req.params['token'];

    //Check that token was provided
    if (!token) return res.json({err: "Missing token"});

    console.log("Received new token: " + token);
    res.send("Token was received");
});

//Getting the user input stock symbol
app.post('/stock_symbol/:symbol', (req, res) => {

    let stock_symbol = req.params['symbol'];
    token = req.body.token;

    //Check that stock symbol was provided
    if (!stock_symbol) return res.send("Missing stock symbol");

    console.log(`Received ${stock_symbol} as stock symbol`);
    setInterval(() => {//Sending the data to the firebase
            fetchData(stock_symbol, (stock_price, error) => {
                if(error){
                    console.log("Error sending message:", error);
                } else {
                    sendResult(token, stock_symbol, stock_price);
                }
            })
        }, 15000);
        res.send("Notification was set!");
});

//This function fetches the data and performs the API GET request
function fetchData (stock_symbol, cb) {
    //Build the full API URL
    let URL = API_URL + stock_symbol + "&apikey=" + API_KEY;
    fetch(URL , {method: 'GET'})
    .then(response => response.json())
    .then((data) => {//Get the desired row of data from the returned response
        cb(data["Global Quote"]["05. price"]);
    })
    .catch((error) => {
        console.error('ERROR: ', error);
        cb("", error);
    });
}

//Send result to user using firebase
function sendResult(token, stock_symbol, stock_price) {
    fcm.send({
        to: token,
        data: {
            symbol: stock_symbol,
            price: stock_price
        },
        notification: {
            title: `UPDATE! - ${stock_symbol} PRICE!`,
            body: `Currently ${stock_symbol} price is ${stock_price}`
        }
    }, (error, response) => {
        console.log("Error: ", error);
    });
}

app.listen(PORT, () => console.log(`Listening on port ${PORT}...`));
