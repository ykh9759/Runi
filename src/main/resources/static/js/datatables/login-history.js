// Call the dataTables jQuery plugin
$(document).ready(function() {
    let dTable = $('#dTable').DataTable({
      dom: '<"row"<"col-sm-12 col-md-6"l><"col-sm-12 col-md-6"f>>rt<"row"<"col-sm-12 col-md-5"i><"col-sm-12 col-md-7"p>>',
      ajax: {
        'url' : '/member/getLoginHistory',
        'type' : 'POST',
        'dataSrc' : ''
      },
      columns: [
        {data: 'no'},
        {data: 'ip'},
        {data: 'inTime'}
      ]
    });
  });
  