const userForm = document.getElementById('userForm');
let userSelected = {};
const formBody = document.getElementById('formBody');
const tBody = document.getElementById('tBody');

const eHeaderPrice = document.getElementById('header-price')

let user = [];

userForm.onsubmit = async (e) => {
    e.preventDefault();
    let data = getDataFromForm(userForm);
    data = {
        ...data,
        // type: {
        //     id: data.type
        // },
        // idCategories: Array.from(eCheckBoxCategories)
        //     .filter(e => e.checked)
        //     .map(e => e.value),
        id: userSelected.id
    }

    let message = "Created"
    if (userSelected.id) {
        await editUser(data);
        webToast.Success({
            status: 'Sửa thành công',
            message: '',
            delay: 2000,
            align: 'topright'
        });
    } else {
        await createUser(data)
        webToast.Success({
            status: 'Thêm thành công',
            message: '',
            delay: 2000,
            align: 'topright'
        });
    }
    await renderTable();
    $('#staticBackdrop').modal('hide');

}

function getDataFromForm(form) {
    event.preventDefault()
    const data = new FormData(form);
    return Object.fromEntries(data.entries())
}

window.onload = async () => {
    await renderTable();
    onLoadSort();

    renderForm(formBody, getDataInput());
}


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
            message: "Email must have minimum is 6 characters and maximum is 20 characters",
            required: true
        },
        {
            label: 'Description',
            name: 'description',
            value: userSelected.description,
            pattern: "^[A-Za-z ]{6,120}",
            message: "Description must have minimum is 6 characters and maximum is 20 characters",
            required: true
        },
        {
            label: 'Price',
            name: 'price',
            value: userSelected.price,
            pattern: "[1-9][0-9]{1,10}",
            message: 'Price errors',
            required: true
        },
        {
            label: 'Images',
            name: 'dob',
            value: userSelected.dob,
            type: 'file',
            required: true,
            // options: product,
            message: 'Please choose Image'
        },

    ];
}


async function findUserById(id) {
    const res = await fetch('/api/user/' + id);
    return await res.json();
}


async function showEdit(id) {
    $('#staticBackdropLabel').text('Edit User');
    clearForm();
    userSelected = await findUserById(id);

    renderForm(formBody, getDataInput());
}


async function getUser() {
    const res = await fetch('/api/user');
    return await res.json();
}

function renderItemStr(item, index) {
    return `<tr>
                    <td>
                        ${index + 1}
                    </td>
                    <td>
                        ${item.name}
                    </td>
                     <td>
                        ${item.email}
                    </td>
                    <td>
                        ${item.description}
                    </td>
                    <td>
                        ${item.price}
                    </td>
                    <td>
                        ${item.dob}
                    </td>
            
                    <td>
                        <a class="btn btn-primary text-white  edit " data-id="${item.id}" data-bs-toggle="modal" data-bs-target="#staticBackdrop">Edit</a>           
                        <a class="btn btn-warning text-white delete" onclick="deleteUser(${item.id})" >Delete</a>
                    </td>
                </tr>`
}

function renderTBody(items) {
    let str = '';
    items.forEach((e, i) => {
        str += renderItemStr(e, i);
    })
    tBody.innerHTML = str;
}

async function renderTable() {
    const response = await fetch(`/api/user`);
    // const pageable = await getRooms();
    // rooms = pageable.content;
    // renderTBody(rooms);

    const result = await response.json();
    // pageable = {
    //     ...pageable,
    //     ...result
    // };
    // genderPagination();
    renderTBody(result.content);
    addEventEditAndDelete();
}

//
// const onSearch = (e) => {
//     e.preventDefault()
//     pageable.search = eSearch.value;
//     pageable.page = 1;
//     renderTable();
// }

// const onLoadSort = () => {
//     eHeaderPrice.onclick = () => {
//         let sort = 'price,desc'
//         if(pageable.sortCustom?.includes('price') &&  pageable.sortCustom?.includes('desc')){
//             sort = 'price,asc';
//             eHeaderPrice.innerHTML = 'Price <i class="bx bxs-up-arrow"></i>';
//         }else {
//             eHeaderPrice.innerHTML = 'Price <i class="bx bxs-down-arrow"></i>';
//         }
//         pageable.sortCustom = sort;
//         renderTable();
//     }
// }

const searchInput = document.querySelector('#search');

searchInput.addEventListener('search', () => {
    onSearch(event)
});

const addEventEditAndDelete = () => {
    const eEdits = tBody.querySelectorAll('.edit');
    const eDeletes = tBody.querySelectorAll('.delete');
    for (let i = 0; i < eEdits.length; i++) {
        console.log(eEdits[i].id)
        eEdits[i].addEventListener('click', () => {
            showEdit(eEdits[i].dataset.id);
        })
    }
}

function clearForm() {
    userForm.reset();
    userSelected = {};
}

function showCreate() {
    $('#staticBackdropLabel').text('Create User');
    clearForm();
    renderForm(formBody, getDataInput())
}

async function editUser(data) {
    const res = await fetch('/api/user/' + data.id, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
}

async function createUser(data) {
    const res = await fetch('/api/user', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
}



