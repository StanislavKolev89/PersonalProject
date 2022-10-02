const changeProfileBtn = document.getElementById('changeProfile');
const inputFields = document.getElementsByTagName('input');

changeProfileBtn.addEventListener('click', editProfile);


function editProfile(event) {

    event.preventDefault();
    for (let inputField of inputFields) {
        inputField.removeAttribute('readonly');
    }

    changeProfileBtn.textContent = 'CONFIRM CHANGES';
    changeProfileBtn.removeEventListener('click', editProfile);


}