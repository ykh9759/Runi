// Call the dataTables jQuery plugin
$(document).ready(function() {
  let dTable = $('#dTable').DataTable({
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

  $('#dTable_wrapper > .row > div').after('<div id="search-area" class="col-sm-12 col-md-6 d-flex justify-content-end"></div>');
  $('#search-area').prepend('<button type="text" id="btn-search" class="btn btn-primary ml-2">검색</button>');
  $('#search-area').prepend('<input type="text" id="search" class="form-control col-2">');
  $('#search-area').prepend('<select id="select" style="width:15%" class="form-select ml-2"></select>');
  $('#search-area').prepend('<input type="text" id="endDate" class="form-control col-2 ml-2" placeholder="yyyy-MM-dd">');
  $('#search-area').prepend('<input type="text" id="startDate" class="form-control col-2 mr-2" placeholder="yyyy-MM-dd">~');
    $('#dTable > thead > tr').children().each(function (indexInArray, valueOfElement) { 
      $('#select').append('<option value='+indexInArray+'>'+valueOfElement.innerHTML+'</option>');
  });

  $('#select').change(function() {
    $('#search').val('');
  });

  $('#btn-search').on('click', function () {
    dTable.ajax.reload();
  });

  $("#search, #startDate, #endDate").on("keyup",function(key){
    if(key.keyCode==13) {
      dTable.ajax.reload();
    }
  });
});
