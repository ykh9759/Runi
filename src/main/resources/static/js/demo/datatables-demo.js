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
    searching: false,
    ajax: {
      'url' : '/member/getProductList',
      'type' : 'POST',
      'dataSrc' : '',
      "data" : function ( d ) {
        d.startDate = $("#startDate").val(),
        d.endDate = $("#endDate").val(),
        d.select = $("#select").val(),
        d.search = $("#search").val()
      }
    },
    // destroy: true,
    columns: [
      {data: 'no'},
      {data: 'productName'},
      {data: 'price'}, 
      {data: 'saveDate'}
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

  $('#productTable_wrapper > .row > div').after('<div id="search-area" class="col-sm-12 col-md-6 d-flex justify-content-end"></div>');
  $('#search-area').prepend('<button type="text" id="btn-search" class="btn btn-primary ml-2">검색</button>');
  $('#search-area').prepend('<input type="text" id="search" class="form-control col-2 d-inline-block">');
  $('#search-area').prepend('<select id="select" style="width:15%" class="form-select d-inline-block ml-2"></select>');
  $('#search-area').prepend('<input type="text" id="endDate" class="form-control col-2 d-inline-block ml-2" placeholder="yyyy-MM-dd">');
  $('#search-area').prepend('<input type="text" id="startDate" class="form-control col-2 d-inline-block mr-2" placeholder="yyyy-MM-dd">~');
    $('#productTable > thead > tr').children().each(function (indexInArray, valueOfElement) { 
      $('#select').append('<option value='+indexInArray+'>'+valueOfElement.innerHTML+'</option>');
  });

  $('#select').change(function() {
    $('#search').val('');
  });

  $('#btn-search').on('click', function () {
    productTable.ajax.reload();
  });

  $("#search, #startDate, #endDate").on("keyup",function(key){
    if(key.keyCode==13) {
      productTable.ajax.reload();
    }
  });
  // $('#productTable_filter').prepend('<select id="select" style="width:15%" class="form-select d-inline-block"></select>');
  // $('#productTable > thead > tr').children().each(function (indexInArray, valueOfElement) { 
  //     $('#select').append('<option value='+indexInArray+'>'+valueOfElement.innerHTML+'</option>');
  // });

  // $('.dataTables_filter > label > input').attr('id','search');
  // $('#select').change(function() {
  //   $('.dataTables_filter > label > input').val('');
  // });
  

  // $('.dataTables_filter > label > input').keyup(function () {
  //   var colIndex = document.querySelector('#select').selectedIndex;
  //   productTable.column(colIndex).search(this.value).draw();
  // });

  // $('#productTable_filter').prepend('<input type="text" id="startDate" class="form-control" placeholder="yyyy-MM-dd"> ');
  // $('#productTable_filter').prepend('<input type="text" id="endDate" class="form-control" placeholder="yyyy-MM-dd">~');

  // $.fn.dataTable.ext.search.push(
  //   function(settings, data, dataIndex){
  //       var min = Date.parse($('#startDate').val());
  //       var max = Date.parse($('#endDate').val());
  //       var targetDate = Date.parse(data[3]);
 
  //       if( (isNaN(min) && isNaN(max) ) || 
  //           (isNaN(min) && targetDate <= max )|| 
  //           ( min <= targetDate && isNaN(max) ) ||
  //           ( targetDate >= min && targetDate <= max) ){ 
  //               return true;
  //       }
  //       return false;
  //   }
  // );

  // $('#toDate, #fromDate').keyup( function() {
  //   productTable.draw();
  // });

});
