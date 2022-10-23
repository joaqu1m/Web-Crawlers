import fetch from 'node-fetch';
import { load } from 'cheerio';

const url = 'http://localhost:8085/';

const response = await fetch(url);
const body = await response.text();

let $ = load(body);

let bodyEl = $('body');

bodyEl.append('<p>An old falcon</p>');

let content = $('body').html();
let items = content.split('\n');

items.forEach((e) => {
    console.log(e.trim());
});