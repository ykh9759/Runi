function checkOnlyOne(element) {
  
  const checkboxes 
    = document.getElementsByName(element.name);

  checkboxes.forEach((cb) => {
  cb.checked = false;
  })

  element.checked = true;
}

function goPost(url) {
  let f = document.createElement('form');
  f.setAttribute('method', 'post');
  f.setAttribute('action', url);
  document.body.appendChild(f);
  f.submit();
}
