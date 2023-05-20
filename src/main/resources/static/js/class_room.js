const base_api = "/api/class_room";
const load = async () => {
  await $.ajax({
    url: `api/major/list`,
    method: "get",
  })
    .done((res) => {
      const rs = parse(res);
      const major = $("#major");
      const major_edit = $("#edit_major");
      if (rs.code == 200) {
        let data = "";

        $.each(rs.data, (index, item) => {
          data += `<option value="${item._id.$oid}">${item.name}</option>`;
        });
        major.html(data);
        major_edit.html(data);
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
                  <td>${item.major_name}</td>
                  <td>${item.class_room_name}</td>
                  <td>${item.class_room_created_at}</td>
                  <td>
                      <div class="d-grid gap-2 d-md-block">
                          <button type="button" class="btn btn-danger btn-sm" onclick="remove('${item._id.$oid}')">Delete</button>
                          <button type="button" class="btn btn-primary btn-sm" onclick="edit('${item._id.$oid}', '${item.major_id.$oid}', '${item.class_room_name}')">Edit</button>
                      </div>
                  </td>
                </tr>`;
        });
        table.html(data);
        return;
      }
    })
    .fail((jqXHR, textStatus) => {
      return alert(textStatus);
    });
};

const data = load();

$("#submit_add").on("submit", (event) => {
  event.preventDefault();
  const major = $("#major").val();
  const name = $("#name").val();
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
      major: major.trim(),
      name: name.trim(),
    },
  })
    .done((response) => {
      const rs = parse(response);
      if (rs.code == 200) {
        load();
        $("#name").val(null);
        return $("#exampleModal").modal("hide");
      }
      return alert(response);
    })
    .fail((jqXHR, textStatus) => {
      return alert(textStatus);
    });
});

$("#submit_edit").on("submit", (event) => {
  event.preventDefault();
  const id = $("#edit_id").val();
  const major = $("#edit_major").val();
  const name = $("#edit_name").val();
  $.ajax({
    beforeSend: function (xhr) {
      xhr.setRequestHeader(
        "Authorization",
        "Bearer " + localStorage.getItem("token")
      );
    },
    url: `${base_api}/update/${id}`,
    method: "post",
    data: {
      major: major.trim(),
      name: name.trim(),
    },
  })
    .done((response) => {
      const rs = parse(response);
      if (rs.code == 200) {
        $("#edit_id").val(null);
        $("#edit_major").val(null);
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

const edit = (id, code, name) => {
  $("#editModal").modal("show");
  $("#edit_id").val(id);
  $("#edit_major").val(code);
  $("#edit_name").val(name);
};

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
