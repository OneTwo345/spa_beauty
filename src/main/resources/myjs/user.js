const userForm = document.getElementById('userForm');
const tBody = document.getElementById('tBody');
const ePagination = document.getElementById('pagination')
const eSearch = document.getElementById('search')
const eSearchButton = document.getElementById('searchButton');
const ePriceRange = document.getElementById('priceRange');
const formBody = document.getElementById('formBody');
const ePrice = document.getElementById('price-check')
let statusCustomer;


let rooms = [];
let userSelected = {};
let pageable = {
    page: 1,
    sort: 'id,desc',
  
}




userForm.onsubmit = async (e) => {
    e.preventDefault();
    let data = getDataFromForm(userForm);
    data = {
        ...data,
    }
    if (userSelected.id) {
        await editRoom(data);
    } else {
        await createRoom(data)
    }
    renderTable();
    $('#staticBackdrop').modal('hide');
}

async function renderTable() {
    const pageable = await getRooms();
    rooms = pageable.content;
    renderTBody(rooms);
    addEventEditAndDelete();
}
async function getRooms() {
    const res = await fetch('/api/users');
    return await res.json();
}
const addEventEditAndDelete = () => {
    const eEdits = tBody.querySelectorAll('.edit');
    const eDeletes = tBody.querySelectorAll('.delete');
    for (let i = 0; i < eEdits.length; i++) {
        console.log(eEdits[i].id)
        eEdits[i].addEventListener('click', () => {
            onShowEdit(eEdits[i].dataset.id);
        })
    }
}

async function  editRoom (data){
    const response = await fetch('/api/users/'+data.id, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    });
    if (response.ok) {
        Swal.fire({
            title: 'Edited',
            text: 'Phòng đã được tạo thành công.',
            icon: 'success',
            confirmButtonText: 'OK'
        }).then(() => {
            location.reload(); // Tải lại trang sau khi tạo phòng
        });
    } else {
        Swal.fire({
            title: 'Error',
            text: 'Có lỗi xảy ra khi tạo phòng.',
            icon: 'error',
            confirmButtonText: 'OK'
        });
    }
}
async function createRoom(data) {

    const response = await fetch('/api/users', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    });
    if (response.ok) {
        Swal.fire({
            title: 'Created',
            text: 'Phòng đã được tạo thành công.',
            icon: 'success',
            confirmButtonText: 'OK'
        }).then(() => {
getList();
        });
    } else {
        Swal.fire({
            title: 'Error',
            text: 'Có lỗi xảy ra khi tạo phòng.',
            icon: 'error',
            confirmButtonText: 'OK'
        });
    }
}
const onShowCreate = () => {
    clearForm();
    $('#staticBackdropLabel').text('Create User');
    renderForm(formBody, getDataInput());

}
document.getElementById('create').onclick = () => {
    onShowCreate();
}
const findById = async (id) => {
    const response = await fetch('/api/users/' + id);
    return await response.json();
}
const onShowEdit = async (id) => {
    clearForm();
    userSelected = await findById(id);
    $('#staticBackdropLabel').text('Edit User');
    $('#staticBackdrop').modal('show');
    $('#name').val(userSelected.name);
    $('#email').val(userSelected.email);
    $('#phone').val(userSelected.phone);
    $('#dob').val(userSelected.dob);
    $('#statusCustomer').val(userSelected.statusCustomer);
    // $('#poster').val(userSelected.poster);
    // $('#image').val(userSelected.poster);
    renderForm(formBody, getDataInput());

}


function clearForm() {
    userForm.reset();
    userSelected = {};
}
function onChangeCheck(selector, value){
    const element = $(selector);
    element.val(value);
    element.change();
}



function getDataFromForm(form) {
    // event.preventDefault()
    const data = new FormData(form);
    return Object.fromEntries(data.entries())
}

function renderItemStr(item) {
    return `<tr>
                    <td>
                        ${item.id}
                    </td>
                    <td>
                        ${item.name}
                    </td>
                    <td>
                        ${item.email}
                    </td>
                    <td>
                        ${item.phone}
                    </td>
                    <td>
                        ${item.dob}
                    </td>
                    <td>
                        ${item.statusCustomer}
                    </td>
                     <td>
            <a class="btn edit" data-id="${item.id}" onclick="onShowEdit(${item.id})">
               <i class="fa-regular fa-pen-to-square text-primary"></i>
            </a>
            <a class="btn delete" data-id="${item.id}" onclick="deleteItem(${item.id})">
                <i class="fa-regular fa-trash-can text-danger"></i>
            </a> 
        </td>
                </tr>`
}
// $(document).ready(function() {
//     $('.item-name').mouseover(function() {
//         const description = $(this).data('description'); // Lấy dữ liệu description từ thuộc tính data-description
//         $('#tooltip-description').text(description); // Đặt nội dung tooltip
//         $('#custom-tooltip').tooltipster({
//             content: $('#tooltip-description'), // Sử dụng tooltipster
//             position: 'right',
//             interactive: true,
//         });
//         $('#custom-tooltip').tooltipster('open'); // Hiển thị tooltip
//     });
// });
function getDataInput() {
    return [
        {
            label: 'Name',
            name: 'name',
            value: userSelected.name,
            required: true,
            pattern: "^[A-Za-z ]{6,20}",
            message: "Username must have minimum is 6 characters and maximum is 20 characters",
        },
        {
            label: 'Email',
            name: 'email',
            value: userSelected.email,
            pattern: "^[A-Za-z ]{6,120}",
            message: "Description must have minimum is 6 characters and maximum is 20 characters",
            required: true
        },


        {
            label: 'Phone',
            name: 'phone',
            value: userSelected.phone,
            pattern: "[1-9][0-9]{1,10}",
            message: 'Price errors',
            required: true
        },
        // {
        //     label: 'Poster',
        //     name: 'poster',
        //     value: userSelected.poster,
        //     pattern: "[1-9][0-9]{1,10}",
        //     message: 'Price errors',
        //     required: true
        // },
    ];
}

async function getList() {
    const response = await fetch(`/api/users?page=${pageable.page - 1 || 0}&sort=${pageable.sortCustom || 'id,desc'}&search=${pageable.search || ''}`);
    const result = await response.json();
    pageable = {
        ...pageable,
        ...result
    };
    genderPagination();

    console.log(result)
    renderTBody(result.content);
}


window.onload = async () => {

    await getList();
    onLoadSort();
    renderForm(formBody, getDataInput());

}
function renderTBody(items) {
    let str = '';
    if (Array.isArray(items)) {
        items.forEach(e => {
            str += renderItemStr(e);
        });
    }
    tBody.innerHTML = str;
}

async function deleteItem(itemId) {
    const { isConfirmed } = await Swal.fire({
        title: 'Xác nhận xóa',
        text: 'Bạn có chắc chắn muốn xóa mục này?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Xóa',
        cancelButtonText: 'Hủy',
    });

    if (!isConfirmed) {
        return; // Người dùng đã hủy xóa
    }

    const response = await fetch(`/api/users/${itemId}`, {
        method: 'DELETE',
    });

    if (response.ok) {
        Swal.fire('Xóa thành công', '', 'success');
        await getList();
    } else {
        Swal.fire('Xóa không thành công', '', 'error');
    }
}

const genderPagination = () => {
    ePagination.innerHTML = '';
    let str = '';
    //generate preview truoc
    str += `<li class="page-item ${pageable.first ? 'disabled' : ''}">
              <a class="page-link" href="#" tabindex="-1" aria-disabled="true">Previous</a>
            </li>`
    //generate 1234

    for (let i = 1; i <= pageable.totalPages; i++) {
        str += ` <li class="page-item ${(pageable.page) === i ? 'active' : ''}" aria-current="page">
      <a class="page-link" href="#">${i}</a>
    </li>`
    }
    //
    //generate next truoc
    str += `<li class="page-item ${pageable.last ? 'disabled' : ''}">
              <a class="page-link" href="#" tabindex="-1" aria-disabled="true">Next</a>
            </li>`
    //generate 1234
    ePagination.innerHTML = str;

    const ePages = ePagination.querySelectorAll('li'); // lấy hết li mà con của ePagination
    const ePrevious = ePages[0];
    const eNext = ePages[ePages.length-1]

    ePrevious.onclick = () => {
        if(pageable.page === 1){
            return;
        }
        pageable.page -= 1;
        getList();
    }
    eNext.onclick = () => {
        if(pageable.page === pageable.totalPages){
            return;
        }
        pageable.page += 1;
        getList();
    }
    for (let i = 1; i < ePages.length - 1; i++) {
        if(i === pageable.page){
            continue;
        }
        ePages[i].onclick = () => {
            pageable.page = i;
            getList();
        }
    }
}
const onSearch = (e) => {
    e.preventDefault()
    pageable.search = eSearch.value;
    pageable.page = 1;
    getList();
}

const searchInput = document.querySelector('#search');

searchInput.addEventListener('search', () => {
    onSearch(event)
});
// const onLoadSort = () => {
//     ePrice.onclick = () => {
//         let sort = 'price,desc'
//         if(pageable.sortCustom?.includes('price') &&  pageable.sortCustom?.includes('desc')){
//             sort = 'price,asc';
//         }
//         pageable.sortCustom = sort;
//         getList();
//     }
// }