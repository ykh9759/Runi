// Call the dataTables jQuery plugin
$(document).ready(function() {
  let productTable = $('#productTable').DataTable({
    dom: '<"m-2"B><"row"<"col-sm-12 col-md-6"l><"col-sm-12 col-md-6"f>>rt<"row"<"col-sm-12 col-md-5"i><"col-sm-12 col-md-7"p>>',
    buttons: [{
      extend: 'excel',
      text: '엑셀',
      footer: true,
      className: 'exportBtn'
    }],
    ajax: {
      'url' : '/member/getProductList',
      'type' : 'POST',
      'dataSrc' : ''
    },
    // destroy: true,
    columns: [
      {data: 'no'},
      {data: 'productName'},
      {data: 'price'}, 
      {data: 'upDate'}
    ],
    columnDefs: [
      {
        targets: 2,
        render : $.fn.dataTable.render.number( ',' )
      }
    ],
    "footerCallback":function(){
      var api = this.api(), data;
      var result = 0;
      api.column(2, {search:'applied'}).data().each(function(data){
          result += parseFloat(data);
      });
      $(api.column(2).footer()).html(result.toLocaleString()+'원');
    },
    "order": [[3, 'desc']]
  });

  $('#productTable_filter').prepend('<select id="select"></select>');
  $('#productTable > thead > tr').children().each(function (indexInArray, valueOfElement) { 
      $('#select').append('<option>'+valueOfElement.innerHTML+'</option>');
  });

  $('#select').change(function() {
    $('.dataTables_filter > label > input').val('');
  });
  

  $('.dataTables_filter > label > input').keyup(function () {
    var colIndex = document.querySelector('#select').selectedIndex;
    productTable.column(colIndex).search(this.value).draw();
  });

  $('#productTable_filter').prepend('<input type="text" id="toDate" placeholder="yyyy-MM-dd"> ');
  $('#productTable_filter').prepend('<input type="text" id="fromDate" placeholder="yyyy-MM-dd">~');

  $.fn.dataTable.ext.search.push(
    function(settings, data, dataIndex){
        var min = Date.parse($('#fromDate').val());
        var max = Date.parse($('#toDate').val());
        var targetDate = Date.parse(data[3]);
 
        if( (isNaN(min) && isNaN(max) ) || 
            (isNaN(min) && targetDate <= max )|| 
            ( min <= targetDate && isNaN(max) ) ||
            ( targetDate >= min && targetDate <= max) ){ 
                return true;
        }
        return false;
    }
);

$('#toDate, #fromDate').keyup( function() {
  productTable.draw();
} );



  
});
