const express = require('express');
const app = express();
app.use(express.json());
const fs = require('fs');

const readTasks = fs.readFileSync('TODO.json'); //reading the JSON file
const allTasks = JSON.parse(readTasks); //parsing the JSON file into JSON object

app.get('/',(req, res) => {
    res.send('test');
});

//Return JSON of all tasks
app.get('/tasks', (req, res) =>{
    res.send(allTasks);
});

app.get('/tasks/new', (req, res) => {
    let id = req.query.id || NaN;
    let task = req.query.task || null;
    //check for valid input
    if (isNaN(id) || !(task)){
        res.send("Invalid input");
    }
    //check if such ID exist in TODO list
    else if (allTasks.hasOwnProperty(id)) {
        res.send('ID already exists');
    }
    //adding the new task to the tasks list
    else {
        allTasks[id] = task;
        const writeTask = JSON.stringify(allTasks, null, 2); // null and 2 arguments are for arraging the file to look like JSON and not like a string 
        fs.writeFile('TODO.json',writeTask,finished);

        function finished(err){
            res.send(`Added ${task} to TODO list`);
        }  
    }
});

app.get('/tasks/remove', (req, res) => {
    let id = req.query.id || NaN ;
    //check for valid input
    if (isNaN(id)){
        res.send("Invalid input");
    }
    //check if such ID exists in TODO list
    else if (!(allTasks.hasOwnProperty(id))) {
        res.send('No such ID exists in TODO list');
    } 
    else {//deleting the required task from the tasks list
        delete allTasks[id];
        const writeTask = JSON.stringify(allTasks, null, 2);// Write the file again without the removed task
        fs.writeFile('TODO.json',writeTask,finished);

        function finished(err){
            res.send(`Deleted task number ${id} from TODO list`);
        }
    }
    
});

app.listen(3000, () => console.log('Listening on port 3000'));


