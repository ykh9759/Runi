// Call the dataTables jQuery plugin
$(document).ready(function() {
  let dTable = $('#dTable').DataTable({
    dom: '<"mb-2"B><"row"<"col-sm-12 col-md-6"l><"col-sm-12 col-md-6"f>>rt<"row"<"col-sm-12 col-md-5"i><"col-sm-12 col-md-7"p>>',
    buttons: [{
      extend: 'excel',
      text: '엑셀',
      className: 'btn-success',
    }],
    searching: false,
    ajax: {
      'url' : '/member/getProductList',
      'type' : 'POST',
      "data" : function ( d ) {
        d.div = 'product',
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
      {data: 'productName'},
      {data: 'price'}, 
      {data: 'saveDate'},
      {data: null,
        render: function(data, type, row) {
            // 버튼을 추가할 컬럼에 대한 렌더링 함수 작성
            return '<button type="button" id="updateModalBtn" data-no="'+data.no+'" data-productname="'+data.productName+'" data-price="'+data.price+'" class="btn btn-primary">수정</button>';
        }
      }
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
    "order": [[0, 'desc']]
  });

  $('#dTable_wrapper > .row > div').after('<div id="search-area" class="col-sm-12 col-md-6 d-flex justify-content-end"></div>');
  $('#search-area').prepend('<button type="text" id="btn-search" class="btn btn-primary ml-2">검색</button>');
  $('#search-area').prepend('<input type="text" id="search" class="form-control col-2">');
  $('#search-area').prepend('<select id="select" style="width:15%" class="form-select ml-2"></select>');
  $('#search-area').prepend('<input type="text" id="endDate" class="form-control col-2 ml-2" placeholder="yyyy-MM-dd">');
  $('#search-area').prepend('<input type="text" id="startDate" class="form-control col-2 mr-2" placeholder="yyyy-MM-dd">~');
  $('#dTable > thead > tr').children().each(function (indexInArray, valueOfElement) { 
    if(valueOfElement.innerHTML != "" && indexInArray != "3" ) {
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

  $(document).on('click', '#updateModalBtn', function() {

    $('#updateModal #no').val($(this).data('no'));
    $('#updateModal #productName').val($(this).data('productname'));
    $('#updateModal #price').val($(this).data('price'));
  
    $('#updateModal').modal('show');
  
  });

  $('#dTable_wrapper > table > td').cl
});
