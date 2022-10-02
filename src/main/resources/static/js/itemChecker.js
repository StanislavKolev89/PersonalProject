const quantityInput = document.getElementById("quantity");
const quantityLeft = document.getElementById("quantityLeft");
const warningMessage= document.getElementById("warningMessage");
const quantityButtons = document.querySelectorAll('button');
const addToCartBtn = document.getElementById('addToCart');

for (let i = 0; i <quantityButtons.length ; i++) {
    quantityButtons[i].addEventListener('click',quantityCheck);
}


function quantityCheck() {
 warningMessage.style.display='none';
        if (parseInt(quantityInput.value) > parseInt(quantityLeft.value)) {
            quantityInput.style.background = 'red';
            warningMessage.style.display='block';
            addToCartBtn.disabled=true;
        }else{
            quantityInput.style.background = "white";
            addToCartBtn.disabled=false;
        }
        document.addEventListener('change',quantityCheck);
}