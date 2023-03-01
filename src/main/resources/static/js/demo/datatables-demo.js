// Call the dataTables jQuery plugin
$(document).ready(function() {
  $('#productTable').DataTable({
    responsive :true,
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
});
