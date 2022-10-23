var request = require('request');
var cheerio = require('cheerio');
var fs = require('fs');

request('http://localhost:8085/', function(err, res, body) {
    if (err) console.log('Erro: ' + err);

    var $ = cheerio.load(body);
    
    $('tr').each(function() {
        var title = $(this).find('td').text().trim();

        console.log(title)
    });
});