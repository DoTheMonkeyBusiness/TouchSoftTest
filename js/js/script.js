const discsArray = document.getElementById("bottom_array");
const rightDiscsArray = document.getElementById("right_discs_array");
const leftDiscsArray = document.getElementById("left_discs_array");
const input = document.getElementById("input");
const btn = document.getElementById("btn");
const titleMessage = document.getElementById("title_message");
const warningNotice = document.getElementById("warning_notice");

let sumLeft = 0;
let sumRight = 0;

btn.addEventListener("click", onBtnClick);

function onBtnClick() {
    if (_isValueValid(parseFloat(input.value))) {
        warningNotice.hidden = true;
        addItemToBottomArray(input.value);
    }
    else {
        warningNotice.hidden = false;
    }
}

//input validation
function _isValueValid(num) {
    return num >= 0 && num <= 20 && num !== "" && (num ^ 0) === num;
}

//adding an item to array
function addItemToBottomArray(value) {
    let child = document.createElement('div');
    child.innerHTML = value;
    child.classList.add('disc_element');
    child.id = 'disc_' + Date.now();
    discsArray.appendChild(child);
}

//adding items to the right
discsArray.addEventListener("contextmenu", function (event) {
    let target = event.target;
    if (target.parentElement.className !== 'bottom_array') return;

    sumRight += parseInt(target.innerHTML);
    addToBarbell(target, "right", rightDiscsArray);
    discsArray.children[target.id].remove();

    checkReadiness();
});

//adding items to the left
discsArray.addEventListener("click", function (event) {
    let target = event.target;
    if (target.parentElement.className !== 'bottom_array') return;

    sumLeft += parseInt(target.innerHTML);
    addToBarbell(target, "left", leftDiscsArray);
    discsArray.children[target.id].remove();

    checkReadiness();
});

//removing items from the right
rightDiscsArray.addEventListener("click", function (event) {
    let target = event.target;
    if (target.parentElement.className !== 'barbell_discs') return;

    sumRight -= parseInt(target.innerHTML);
    addItemToBottomArray(target.innerHTML);
    rightDiscsArray.children[target.id].remove();

    checkReadiness();
});

//removing items from the left
leftDiscsArray.addEventListener("click", function (event) {
    let target = event.target;
    if (target.parentElement.className !== 'barbell_discs') return;

    sumLeft -= parseInt(target.innerHTML);
    addItemToBottomArray(target.innerHTML);
    leftDiscsArray.children[target.id].remove();

    checkReadiness();
});

//adding items to the left or right
function addToBarbell(target, location, locArray) {
    let child = document.createElement('div');
    child.innerHTML = target.innerHTML;
    child.classList.add('disc_element');
    child.id = location + '_disc_' + Date.now();
    locArray.appendChild(child);
}

//check readiness of barbell
function checkReadiness() {
    if (sumLeft === sumRight && sumRight !== 0) {
        titleMessage.innerHTML = "Barbell is ready";
    } else {
        titleMessage.innerHTML = "TouchSoft Test"
    }
}