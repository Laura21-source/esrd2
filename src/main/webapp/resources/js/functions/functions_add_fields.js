// Добавить GROUP_FIELDS
$(document).on("click", ".addBlock", function() {
    var id = $(this).attr('data-group');
    var idBlock = '#blockDiv'+id+' .blockField';
    //alert(idBlock);
    var linksOld = parseInt($(idBlock+" .blockGroup [data-block='1']").length);
    var pole = $(this).attr('data-value');
    linksOld = linksOld + 1;
    // Найдем максимальное значение атрибута
    var links = null;
    $(idBlock+" .blockGroup").each(function() {
        var value = parseInt($(this).attr('data-move'));
        links = (value > links) ? value : links;
    });
    links = links+1;
    var asd = $("#selectType").val();
    groupNewFields(idBlock, id, links, '', linksOld, '');
    var docId = getId();
    if(docId > 0) {
        getStack ('rest/profile/docs/'+docId, pole, links, '', docId);
    } else {
        getStack ('rest/profile/doctypes/'+asd+'/fields', pole, links, '', '');
        //console.log('rest/profile/doctypes/'+asd+'/fields', pole, links, '', '');
    }
});

// Добавить GROUP_FIELDS во вторичном документе
$(document).on("click", ".addBlockNew", function() {
    var id = $(this).attr('data-group');
    var idBlock = '#blockDivNew'+id+' .blockField';
    //alert(idBlock);
    var linksOld = parseInt($(idBlock+" .blockGroupNew [data-block='2']").length);
    var pole = $(this).attr('data-value');
    linksOld = linksOld + 1;
    // Найдем максимальное значение атрибута
    var links = null;
    $(idBlock+" .blockGroupNew").each(function() {
        var value = parseInt($(this).attr('data-move'));
        links = (value > links) ? value : links;
    });
    links = links+1;
    //alert(links);
    var asd = $("#selectTypeNew").val();
    //console.log(idBlock, id, links, '', linksOld, 1);
    groupNewFields(idBlock, id, links, '', linksOld, 1);
    getStack ("rest/profile/doctypes/" + asd + "/fields", pole, links, 1);
});

// Добавить пользователя
$(document).on("click", ".addUser", function() {
    //var links = $('[data-user="1"]').length;
    var links = $(".blockUser:last").attr('id');
    links = parseInt(links.substr(9));
    var links1 = links + 1;
    var fieldUser = '#userList'+links1;
    $('#userListBlock').append(
        '<div class="col-12 blockUser" id="blockUser'+links1+'">' +
        '   <div class="row d-flex align-items-center justify-content-center fontSmall userListBlock" data-user="1">' +
        '       <div class="col-md-1">'+links1+'</div>' +
        '       <div class="col-md-1"><i class="fas fa-user"></i></div>' +
        '       <div class="col-md-8 selectUser">' +
        '           <select data-placeholder="Выберите из справочника" class="chosen-select userList" data-spisok="'+links1+'" id="userList'+links1+'" name="userList[]" required>' +
        '               <option value="">Выбрать</option>' +
        '           </select>' +
        '           <div class="fontSmall text-left" id="userListPost'+links1+'"></div>' +
        '       </div>' +
        '       <div class="col-md-2">' +
        '           <div id="delUser'+links1+'" class="btn btn-danger btn-sm pointer delUser rounded px-3" title="Удалить пользователя">' +
        '               <i class="fas fa-trash"></i>' +
        '           </div>' +
        '       </div>' +
        '    </div>' +
        '</div>');
    $('#blockUser'+links1+' select').chosen({
        width: "100%",
        no_results_text: "Ничего не найдено!"
    });
    var newField = createOptions ('rest/profile/users/', fieldUser, '', 'id', '', 'usersList');
    $('#userList'+links1).append(newField);
});

// Добавить пользователя во вторичном документе
$(document).on("click", ".addUserNew", function() {
    //var links = $('[data-user="1"]').length;
    var links = $(".blockUserNew:last").attr('id');
    links = parseInt(links.substr(12));
    var links1 = links + 1;
    var fieldUser = '#userListNew'+links1;
    $('#userListBlockNew').append(
        '<div class="col-12 blockUserNew" id="blockUserNew'+links1+'">' +
        '   <div class="row d-flex align-items-center justify-content-center fontSmall userListBlockNew" data-user="1">' +
        '     <div class="col-md-1">'+links1+'</div>' +
        '     <div class="col-md-1"><i class="fas fa-user"></i></div>' +
        '     <div class="col-md-8 selectUser">' +
        '         <select data-placeholder="Выберите из справочника" class="chosen-select userListNew" data-spisok="'+links1+'" id="userListNew'+links1+'" name="userListNew[]" required>' +
        '             <option value="">Выбрать</option>' +
        '         </select>' +
        '         <div class="fontSmall text-left" id="userListPostNew'+links1+'"></div>' +
        '     </div>' +
        '     <div class="col-md-2">' +
        '         <div id="delUserNew'+links1+'" class="btn btn-danger btn-sm pointer delUser rounded px-3" title="Удалить пользователя">' +
        '             <i class="fas fa-trash"></i>' +
        '         </div>' +
        '     </div>' +
        '   </div>' +
        '</div>');
    $('#blockUserNew'+links1+' select').chosen({
        width: "100%",
        no_results_text: "Ничего не найдено!"
    });
    var newField = createOptions ('rest/profile/users/', fieldUser, '', 'id', '', 'usersList');
    $('#userListNew'+links1).append(newField);
});