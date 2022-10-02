
const categoryOption = document.getElementById('categoryOption');

categoryOption.addEventListener('change', filterByCategory)
let elementsByClassName = document.getElementsByClassName('filter');

function filterByCategory() {
  if(categoryOption.value=="noCategory"){
    for (const div of elementsByClassName) {
        div.parentElement.style.display='block';
      }
    return;
    }

  for (const div of elementsByClassName) {
    if(div.id!=categoryOption.value){
      div.parentElement.style.display='none';
    }else{
      div.parentElement.style.display='block';
    }
  }
  };

