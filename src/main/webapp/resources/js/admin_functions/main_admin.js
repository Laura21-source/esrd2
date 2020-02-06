function getId() {
    return new URL(window.location.href).searchParams.get("id");
}

// function docType() {
//     var getId1 = getId();
//         $.ajax({
//             url: "rest/profile/doctypes",
//             method: "GET",
//             contentType: "application/json",
//             success: function (data) {
//                 if(getId1 === data[i].id) {
//                     var a =  data[i].name;
//                 }
//                 return a;
//             }
//         });
//     console.log(a);
// }
