const express = require('express');
const app = express();
app.use(express.static('./public'));
const fs = require('fs');
app.listen(3000, () => console.log('Listening on port 3000'));

app.get('/getfile', (req,res) => {
    let filename = req.query.filename || null;
    //Checking for valid input
    if (!(filename)){
        res.send("Invalid input");
        return; //Don't move to the next condition
    }
    //Checking that input file exists in ./public folder
    if(!(fs.existsSync(`./public/${filename}`))){
        res.send("No such file exists, please try again");
        return; //Don't move to the next condition
    }
    //Reading the file and piping it to the res
    fs.createReadStream(`./public/${filename}`).pipe(res);
});