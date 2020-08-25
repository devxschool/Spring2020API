const express = require('express');
var mysql = require('mysql');
const bodyParser = require('body-parser');
const app = express();
const port = 300;

app.use(bodyParser.urlencoded({extended: false}));
app.set('view engine', 'pug');

var connection = mysql.createConnection({
    host : '18.218.51.74',
    port : '3306',
    user : 'uiuser',
    password : 'P@ssw0rd',
    database : 'test'
});

    connection.connect(function(err){
    if(err) throw err;
    console.log('Connected...');
    })


app.get('/', function (req, res) {
    res.sendFile('index.html', { root: __dirname })
});

app.get('/employees', function (req, res) {
    connection.query("SELECT * FROM employees", function(err, rows, fields){
        if(err) throw err;
        res.render('employees', {title: 'Employees Details', items: rows})
    });
});

app.post('/submit', function (req, res) {
    console.log(req.body);
    var query = `INSERT INTO employees VALUES(0,'${req.body.last_name}','${req.body.name}','${req.body.extension}', '${req.body.email}', '${req.body.office_code}', ${req.body.reports_to}, '${req.body.job_title}');`;
    connection.query(query, function(err){
        if (err) throw err;
        res.render('index', {title: 'Data Saved', message: 'Data Saved succesfully.' })
    });

});
connection.end;
app.listen(port, () => console.log(`Example app listenning on port ${port}`));
