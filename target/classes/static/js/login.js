const base_api = "/api/user";
const parse = (json) => {
  return JSON.parse(json);
};

if (localStorage.getItem("email")) {
  $("#inputEmail").val(localStorage.getItem("email"));
}

if (localStorage.getItem("password")) {
  $("#inputPassword").val(localStorage.getItem("password"));
}

$("#submitLogin").on("submit", (event) => {
  event.preventDefault();
  $.ajax({
    url: `${base_api}/login`,
    method: "post",
    data: {
      email: $("#inputEmail").val(),
      password: $("#inputPassword").val(),
    },
  })
    .done((response) => {
      const rs = parse(response);
      if (rs.code == 200) {
        const remember = $("#inputRememberPassword").val();

        localStorage.setItem("token", "mytoken");
        if (remember) {
          localStorage.setItem("email", $("#inputEmail").val());
          localStorage.setItem("password", $("#inputPassword").val());
        } else {
          localStorage.setItem("email", "");
          localStorage.setItem("password", "");
        }
        window.location.assign("/");
        return;
      } else {
        alert(response);
        window.location.assign("/login");
        return;
      }
    })
    .fail((jqXHR, textStatus) => {
      window.location.assign("/login");
      return alert(textStatus);
    });
});
