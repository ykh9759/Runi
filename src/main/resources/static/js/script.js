function checkOnlyOne(element) {
  
    const checkboxes 
        = document.getElementsByName(element.name);
    
    checkboxes.forEach((cb) => {
      cb.checked = false;
    })
    
    element.checked = true;
  }