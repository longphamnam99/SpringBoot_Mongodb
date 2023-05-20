const base_api = "/api/major";

const load = () => {
  $.ajax({
    url: `${base_api}/list`,
    method: "get",
  })
    .done((response) => {
      const rs = parse(response);
      const table = $(".table tbody");
      if (rs.code == 200) {
        let data = "";
        let count = 0;
        $.each(rs.data, (index, item) => {
          count++;
          data += `<tr>
                <td>${count}</td>
                <td>${item.code}</td>
                <td>${item.name}</td>
                <td>${item.created_at}</td>
                <td>
                    <div class="d-grid gap-2 d-md-block">
                        <button type="button" class="btn btn-danger btn-sm" onclick="remove('${item._id.$oid}')">Delete</button>
                        <button type="button" class="btn btn-primary btn-sm" onclick="edit('${item._id.$oid}', '${item.code}', '${item.name}')">Edit</button>
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
  const code = $("#code").val();
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
      code: code.trim().toUpperCase(),
      name: ucfirst(name),
    },
  })
    .done((response) => {
      const rs = parse(response);
      if (rs.code == 200) {
        $("#code").val(null);
        $("#name").val(null);
        load();
        return $("#exampleModal").modal("hide");
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

const edit = (id, code, name) => {
  $("#editModal").modal("show");
  $("#edit_id").val(id);
  $("#edit_code").val(code);
  $("#edit_name").val(name);
};

$("#submit_edit").on("submit", (event) => {
  event.preventDefault();
  const id = $("#edit_id").val();
  const code = $("#edit_code").val();
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
      code: code.trim().toUpperCase(),
      name: ucfirst(name),
    },
  })
    .done((response) => {
      const rs = parse(response);
      if (rs.code == 200) {
        $("#edit_id").val(null);
        $("#edit_code").val(null);
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
