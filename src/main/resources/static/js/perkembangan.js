let counterYa = 0;
let counterQuestion = 0;
let gerakKasar = 0;
let gerakHalus = 0;
let bicara = 0;
let sosialisasi = 0;

function checkAdder() {
    if ((document.getElementById('radioYa').checked) || (document.getElementById('radioTidak').checked)) {
        add();
        counterQuestion++;

        document.getElementById('image').setAttribute('src', tmpImg[counterQuestion]);
        document.getElementById('progressText').innerHTML = counterQuestion + '/' + size;
        document.getElementById('question').innerHTML = tmp[counterQuestion];
        document.getElementById('radioYa').checked = false;
        document.getElementById('radioTidak').checked = false
        var i = (counterQuestion*10).toString();

        console.log(counterQuestion);
        if(counterQuestion == size-1) {
            console.log("masuk");
            document.getElementById('buttonNext').setAttribute('style', 'display: none');
            document.getElementById('buttonModal').setAttribute('style', 'display: block');
        }
    }
}

function add() {
    if(document.getElementById('radioYa').checked) {
        counterYa++;
        const tipe = tmpMap[tmp[counterQuestion]];
        console.log(tipe);
        if (tipe == 'Gerak Halus') {gerakHalus++}
        if (tipe == 'Gerak Kasar') {gerakKasar++}
        if (tipe == 'Bicara dan Bahasa') {bicara++}
        if (tipe == 'Sosialisasi dan Kemandirian') {sosialisasi++}
    }
}

function toSubmit() {
    if ((document.getElementById('radioYa').checked) || (document.getElementById('radioTidak').checked)) {
        add();
        document.getElementById('result').setAttribute('value', counterYa);
        document.getElementById('resultGH').setAttribute('value', gerakHalus);
        document.getElementById('resultGK').setAttribute('value', gerakKasar);
        document.getElementById('resultB').setAttribute('value', bicara);
        document.getElementById('resultS').setAttribute('value', sosialisasi);
        // document.getElementById('buttonModal').setAttribute('data-bs-toggle', 'modal');
        // document.getElementById('buttonModal').setAttribute('data-bs-target', '#modalSave');
    }
}
