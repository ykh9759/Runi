// Call the dataTables jQuery plugin
$(document).ready(function() {
  let dTable = $('#dTable').DataTable({
    dom: '<"mb-2"B><"row"<"col-sm-12 col-md-6"l><"col-sm-12 col-md-6"f>>rt<"row"<"col-sm-12 col-md-5"i><"col-sm-12 col-md-7"p>>',
    buttons: [
      {
        extend: 'excel',
        text: '엑셀',
        className: 'btn-success',
        title: ''
      }
    ],
    searching: false,
    ajax: {
      'url' : '/member/getOrderList',
      'type' : 'POST',
      "data" : function ( d ) {
        d.div = 'order',
        d.status = $("#status").val(),
        d.startDate = $("#startDate").val(),
        d.endDate = $("#endDate").val(),
        d.select = $("#select").val(),
        d.search = $("#search").val()
      },
      'dataSrc' : '',
      'error' : function(xhr, error, thrown) {

        if(xhr.responseJSON.valid_startDate) {
          Swal.fire({
            text: xhr.responseJSON.valid_startDate,
            icon: 'warning',
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '확인',
          });
        }else if(xhr.responseJSON.valid_endDate) {
          Swal.fire({
            text: xhr.responseJSON.valid_endDate,
            icon: 'warning',
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '확인',
          });
        } 
      }
    },
    columns: [
      {data: 'no'},
      {data: 'name'},
      {data: 'phone'}, 
      {
        data: null,
        render: function(data, type, row) {
          return data['address'] + ' ' + data['address2'];
        }
      },
      {data: 'accountName'},
      {data: 'parcel'},
      {data: 'cashReceipts'},
      {data: 'plist'},
      {data: 'price'},
      {data: 'inTime'},
    ],
    columnDefs: [
      {
        targets: 8,
        render : $.fn.dataTable.render.number( ',' )
      }
    ],
    "footerCallback":function(){
      var api = this.api(), data;
      var result = 0;
      api.column(8, {search:'applied'}).data().each(function(data){
          result += parseFloat(data);
      });
      $(api.column(8).footer()).html(result.toLocaleString()+'원');
    },
    "order": [[3, 'desc']]
  });

  $('#dTable_wrapper > .row > div').after('<div id="search-area" class="col-sm-12 col-md-6 d-flex justify-content-end"></div>');
  $('#search-area').prepend('<input type="hidden" id="status" class="form-control">');
  $('#search-area').prepend('<button type="text" id="btn-search" class="btn btn-primary ml-2">검색</button>');
  $('#search-area').prepend('<input type="text" id="search" class="form-control col-2">');
  $('#search-area').prepend('<select id="select" style="width:15%" class="form-select ml-2"></select>');
  $('#search-area').prepend('<input type="text" id="endDate" class="form-control col-2 ml-2" placeholder="yyyy-MM-dd">');
  $('#search-area').prepend('<input type="text" id="startDate" class="form-control col-2 mr-2" placeholder="yyyy-MM-dd">~');
    $('#dTable > thead > tr').children().each(function (indexInArray, valueOfElement) { 
      if(indexInArray != "9" ) {
        $('#select').append('<option value='+indexInArray+'>'+valueOfElement.innerHTML+'</option>');
      }
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

  $('#receiptBtn').on('click', function () {
    $('#status').val('A');
    dTable.ajax.reload();
  });

  $('#cancelBtn').on('click', function () {
    $('#status').val('C');
    dTable.ajax.reload();
  });

  $('#successBtn').on('click', function () {
    $('#status').val('S');
    dTable.ajax.reload();
  });

});
