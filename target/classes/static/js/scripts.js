/*!
 * Start Bootstrap - SB Admin v7.0.7 (https://startbootstrap.com/template/sb-admin)
 * Copyright 2013-2023 Start Bootstrap
 * Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-sb-admin/blob/master/LICENSE)
 */
//
// Scripts
//

if (localStorage.getItem("token") === null) {
  window.location.assign("/login");
}

if (localStorage.getItem("token") != "mytoken") {
  window.location.assign("/login");
}

if (localStorage.getItem("email")) {
  $("#user_login").text(localStorage.getItem("email"));
}

const headers = (xhr) => {
  return xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem("token"));
}

const ucfirst = (string) => {
  return string.charAt(0).toUpperCase() + string.slice(1);
};

const parse = (json) => {
  return JSON.parse(json);
};

window.addEventListener("DOMContentLoaded", (event) => {
  // Toggle the side navigation
  const sidebarToggle = document.body.querySelector("#sidebarToggle");
  if (sidebarToggle) {
    // Uncomment Below to persist sidebar toggle between refreshes
    // if (localStorage.getItem('sb|sidebar-toggle') === 'true') {
    //     document.body.classList.toggle('sb-sidenav-toggled');
    // }
    sidebarToggle.addEventListener("click", (event) => {
      event.preventDefault();
      document.body.classList.toggle("sb-sidenav-toggled");
      localStorage.setItem(
        "sb|sidebar-toggle",
        document.body.classList.contains("sb-sidenav-toggled")
      );
    });
  }
});

const logout = () => {
  localStorage.setItem("token", "");
  window.location.assign("/login");
};
