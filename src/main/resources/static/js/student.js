const base_api = "/api/student";

function calculateAge(dateOfBirth) {
  const today = new Date();
  const birthDate = new Date(dateOfBirth);
  let age = today.getFullYear() - birthDate.getFullYear();
  const monthDifference = today.getMonth() - birthDate.getMonth();

  if (
    monthDifference < 0 ||
    (monthDifference === 0 && today.getDate() < birthDate.getDate())
  ) {
    age--;
  }

  return age;
}

const load = async () => {
  await $.ajax({
    beforeSend: function (request) {
      request.setRequestHeader("Authority", "sdsd");
    },
    url: `/api/class_room/list`,
    method: "get",
  })
    .done((res) => {
      const rs = parse(res);
      const classRoom = $("#class_room_id");
      const classRoom_edit = $("#edit_class_room_id");
      if (rs.code == 200) {
        let data = "";

        $.each(rs.data, (index, item) => {
          data += `<option value="${item._id.$oid}">${item.class_room_name}</option>`;
        });
        classRoom.html(data);
        classRoom_edit.html(data);
      }
    })
    .fail((jqXHR, textStatus) => {
      return alert(textStatus);
    });
  await $.ajax({
    url: `${base_api}/list`,
    method: "get",
  })
    .done((response) => {
      const rs = parse(response);
      const table = $(".table tbody");
      if (rs.code == 200) {
        let data = "";
        let count = 0;
        $.each(rs.data, async (index, item) => {
          count++;
          data += `<tr>
                  <td>${count}</td>
                  <td>${item.name}</td>
                  <td>${item.class_room_name}</td>
                  <td>${item.major_name}</td>
                  <td>${parseInt(item.gender) ? "Nam" : "Nữ"}</td>
                  <td>${calculateAge(item.date_of_birth)}</td>
                  <td>${item.created_at}</td>
                  <td>
                      <div class="d-grid gap-2 d-md-block">
                          <button type="button" class="btn btn-danger btn-sm" onclick="remove('${
                            item._id.$oid
                          }')">Delete</button>
                          <button type="button" class="btn btn-primary btn-sm" onclick="edit('${
                            item._id.$oid
                          }', '${item.name}', '${item.class_room_id.$oid}', '${
            item.gender
          }', '${item.date_of_birth}')">Edit</button>
                      </div>
                  </td>
                </tr>`;
        });
        table.html(data);
        return;
      }
      return alert(response);
    })
    .fail((jqXHR, textStatus) => {
      return alert(textStatus);
    });
};

load();

$("#submit_add").on("submit", (event) => {
  event.preventDefault();
  const name = $("#name").val();
  const class_room_id = $("#class_room_id").val();
  const gender = $("#gender").val();
  const date_of_birth = $("#date_of_birth").val();
  $.ajax({
    beforeSend: function (xhr) {
      xhr.setRequestHeader(
        "Authorization",
        "Bearer " + localStorage.getItem("token")
      );
    },
    url: `${base_api}/add`,
    method: "post",
    data: {
      name: ucfirst(name),
      class_room_id: class_room_id,
      date_of_birth: date_of_birth,
      gender: gender,
    },
  })
    .done((response) => {
      const rs = parse(response);
      if (rs.code == 200) {
        $("#name").val(null);
        $("#date_of_birth").val(null);
        load();
        return $("#exampleModal").modal("hide");
      }
      return alert(response);
    })
    .fail((jqXHR, textStatus) => {
      return alert(textStatus);
    });
});

const edit = (id, name, class_room_id, gender, date_of_birth) => {
  $("#editModal").modal("show");
  $("#edit_id").val(id);
  $("#edit_name").val(name);
  $("#edit_class_room_id").val(class_room_id);
  $("#edit_gender").val(gender);
  $("#edit_date_of_birth").val(date_of_birth);
};

$("#submit_edit").on("submit", (event) => {
  event.preventDefault();
  $.ajax({
    beforeSend: function (xhr) {
      xhr.setRequestHeader(
        "Authorization",
        "Bearer " + localStorage.getItem("token")
      );
    },
    url: `${base_api}/update/${$("#edit_id").val()}`,
    method: "post",
    data: {
      name: $("#edit_name").val(),
      class_room_id: $("#edit_class_room_id").val(),
      gender: $("#edit_gender").val(),
      date_of_birth: $("#edit_date_of_birth").val(),
    },
  })
    .done((response) => {
      const rs = parse(response);
      if (rs.code == 200) {
        $("#edit_name").val(null);
        load();
        return $("#editModal").modal("hide");
      }
      return alert(response);
    })
    .fail((jqXHR, textStatus) => {
      return alert(textStatus);
    });
});

const remove = (id) => {
  $.ajax({
    beforeSend: function (xhr) {
      xhr.setRequestHeader(
        "Authorization",
        "Bearer " + localStorage.getItem("token")
      );
    },
    url: `${base_api}/delete/${id}`,
    method: "delete",
  })
    .done((response) => {
      const rs = parse(response);
      if (rs.code == 200) return load();
      return alert(response);
    })
    .fail((jqXHR, textStatus) => {
      return alert(textStatus);
    });
};
