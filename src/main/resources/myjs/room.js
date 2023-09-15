const roomForm = document.getElementById('roomForm');
const eCheckBoxAuthors = document.getElementsByName('authors');
const tBody = document.getElementById('tBody');
const ePagination = document.getElementById('pagination')
const eSearch = document.getElementById('search')
const eSearchButton = document.getElementById('searchButton');
const ePriceRange = document.getElementById('priceRange');
const formBody = document.getElementById('formBody');
const ePrice = document.getElementById('price-check')
let authors;
let categories;
let status;
let rooms = [];

let roomSelected = {};
let pageable = {
    page: 1,
    sort: 'id,desc',
    search: '',
    min: 1,
    max: 50000000000000,
}
// Lấy tham chiếu đến phần tử <span> và tham chiếu đến lớp "arrow-up"
const priceSpan = document.querySelector('.arrow');
const arrowUpClass = 'arrow-up';

priceSpan.addEventListener('click', function() {
    if (priceSpan.classList.contains(arrowUpClass)) {
        priceSpan.innerHTML = 'Price &#9650;'; // Ngược lên
        priceSpan.classList.remove(arrowUpClass);
    } else {
        priceSpan.innerHTML = 'Price &#9660;'; // Ngược xuống
        priceSpan.classList.add(arrowUpClass);
    }
});
$(document).ready(function () {
    $('.js-example-basic-single').select2({
        dropdownParent: $('#staticBackdrop')
    });
    $('.js-example-basic-multiple').select2({
        dropdownParent: $('#staticBackdrop')
    })
});
ePriceRange.onchange= () => {
    const priceRange = ePriceRange.value;
    const [min, max] = priceRange.split('-').map(Number);

    searchByPrice(min, max);
    getList();
};

function searchByPrice(min, max) {
    const minPrice = parseFloat(min);
    const maxPrice = parseFloat(max);
    pageable.min = minPrice;
    pageable.max = maxPrice;
 getList();

}
$(document).ready(function () {
    $('#authors').select2({
        dropdownParent: $('#staticBackdrop'),
        data: authors, // Populate the authors data here
    });
    const select = document.getElementsByClassName('select2-selection')[0].style;
    select.borderRadius = '0';
    // select.background ='black'

});
roomForm.onsubmit = async (e) => {
    const idAuthors = $("#authors").select2('data').map(e => e.id);

    e.preventDefault();
    let data = getDataFromForm(roomForm);
    data = {
        ...data,
        categories: {
            id: data.categories
        },
        idAuthors,
    }
    if(data.authors.length === 0){
        alertify.error('Please select an author!');
        return;
    }
    if (roomSelected.id) {
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
    const res = await fetch('/api/books');
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
    const response = await fetch('/api/books/'+data.id, {
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

    const response = await fetch('/api/books', {
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
    $('#staticBackdropLabel').text('Create Book');
    renderForm(formBody, getDataInput());

}
document.getElementById('create').onclick = () => {
    onShowCreate();
}
const findById = async (id) => {
    const response = await fetch('/api/books/' + id);
    return await response.json();
}
const onShowEdit = async (id) => {
    clearForm();
    roomSelected = await findById(id);
    $('#staticBackdropLabel').text('Edit Book');
    $('#staticBackdrop').modal('show');
    $('#name').val(roomSelected.name);
    $('#publicDate').val(roomSelected.publicDate);
    $('#price').val(roomSelected.price);
    checkAuthorsCheckBox();

    $('#status').val(roomSelected.status);
    onChangeCheck('#categories', roomSelected.categoriesId);
    renderForm(formBody, getDataInput());

}
function checkAuthorsCheckBox() {
    roomSelected.authorsId.forEach(authorId => {
        const checkbox = document.querySelector(`input[name="authors"][value="${authorId}"]`);
        if (checkbox) {
            checkbox.checked = true;
        }
    });
}

function clearForm() {
    roomForm.reset();
    roomSelected = {};
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
function formatCurrency(number) {
    return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(number);
}
function renderItemStr(item, index) {
    return `<tr>
                    <td>
                        ${index +1}
                    </td>
                    <td>
                        ${item.name}
                    </td>
                    <td>
                        ${item.description}
                    </td>
                    <td>
                        ${formatCurrency(item.price)}
                    </td>
                    <td>
                        ${item.poster}
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
$(document).ready(function() {
    $('.item-name').mouseover(function() {
        const description = $(this).data('description'); // Lấy dữ liệu description từ thuộc tính data-description
        $('#tooltip-description').text(description); // Đặt nội dung tooltip
        $('#custom-tooltip').tooltipster({
            content: $('#tooltip-description'), // Sử dụng tooltipster
            position: 'right',
            interactive: true,
        });
        $('#custom-tooltip').tooltipster('open'); // Hiển thị tooltip
    });
});
function getDataInput() {
    return [
        {
            label: 'Title',
            name: 'title',
            value: roomSelected.title,
            required: true,
            pattern: "^[A-Za-z ]{6,20}",
            message: "Username must have minimum is 6 characters and maximum is 20 characters",
        },
        {
            label: 'Description',
            name: 'description',
            value: roomSelected.description,
            pattern: "^[A-Za-z ]{6,120}",
            message: "Description must have minimum is 6 characters and maximum is 20 characters",
            required: true
        },
        {
            label: 'Publish Date',
            name: 'publishDate',
            value: roomSelected.publishDate,
            type: 'date',
            // pattern: "^[A-Za-z ]{6,120}",
            // message: "Description must have minimum is 6 characters and maximum is 20 characters",
            required: true
        },
        {
            label: "Type",
            name: "status",
            value: roomSelected.status,
            type: "select",
            require: true,
            message: "Type invalid",
            options: [{value: "MULTIPLE", name:"Multiple"},{value: "SINGLE", name:"Single"}],
        },
        {
            label: 'Price',
            name: 'price',
            value: roomSelected.price,
            pattern: "[1-9][0-9]{1,10}",
            message: 'Price errors',
            required: true
        },
        {
            label: 'Categories',
            name: 'categories',
            value: roomSelected.categoriesId,
            type: 'select',
            required: true,
            options: categories,
            message: 'Please choose Type'
        },



    ];
}

async function getList() {
    const response = await fetch(`/api/books?page=${pageable.page - 1 || 0}&sort=${pageable.sortCustom || 'id,desc'}&search=${pageable.search || ''}&min=${pageable.min || ''}&max=${pageable.max || ''}`);
    const result = await response.json();
    pageable = {
        ...pageable,
        ...result
    };
    genderPagination();

    console.log(result)
    renderTBody(result.content);
}
async function getCategoriesSelectOption() {
    const res = await fetch('api/categories');
    return await res.json();
}

async function getAuthorsSelectOption() {
    const res = await fetch('api/authors');
    return await res.json();
}

window.onload = async () => {
    categories = await getCategoriesSelectOption();
    authors = await getAuthorsSelectOption();
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

    const response = await fetch(`/api/books/${itemId}`, {
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
const onLoadSort = () => {
    ePrice.onclick = () => {
        let sort = 'price,desc'
        if(pageable.sortCustom?.includes('price') &&  pageable.sortCustom?.includes('desc')){
            sort = 'price,asc';
        }
        pageable.sortCustom = sort;
        getList();
    }
}