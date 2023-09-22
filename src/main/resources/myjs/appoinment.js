const billForm = document.getElementById("billForm");
const selectProducts = document.getElementById("selectProducts");
let products = {};
let combos = {};
// submit form

function getData() {
    const idProduct = $("#selectProducts").select2('data').map(e => e.id);
    const idCombo = $('#selectCombos').select2('data').map(e => e.id);
    const price = 100.0;

    // lay data tu form
    let data = getDataFromForm(billForm);

    data = {
        ...data,
        idProduct,
        idCombo,
        price,
        appointmentTime: convertDate(data.appointmentTime + "")
    }
    return data;
}

function getDataFromForm(form) {
    const data = new FormData(form);
    return Object.fromEntries(data.entries());
}

async function createBill(data) {
    const response = await fetch('/api/bills', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    });
    if (response.ok) {
        Swal.fire({
            title: 'Created',
            text: 'Cuộc hẹn đã được tạo thành công.',
            icon: 'success',
            confirmButtonText: 'OK'
        }).then(() => {

        });
    } else {
        Swal.fire({
            title: 'Error',
            text: 'Có lỗi xảy ra',
            icon: 'error',
            confirmButtonText: 'OK'
        });
    }

}

async function initElementBillForm() {
    try {
        const products = await getProducts();


        const combos = await getCombos();
        console.log(combos);

        products.map((item) => {
            let option = document.createElement("option");
            option.value = item.id;
            option.innerHTML = item.name;
            selectProducts.appendChild(option);
        });


        combos.map((item) => {
            let option = document.createElement("option");
            option.value = item.id;
            option.innerHTML = item.name;
            selectCombos.appendChild(option);
        });
    } catch (error) {
        console.error(error);
    }
}

async function getProducts() {
    try {
        const response = await fetch(`/api/products/list`);
        return await response.json();
    } catch (error) {
        throw new Error("Failed to fetch products.");
    }
}

async function getCombos() {
    try {
        const response = await fetch(`/api/combos/list`);
        return await response.json();
    } catch (error) {
        throw new Error("Failed to fetch products.");
    }
}

$(document).ready(function () {
    initElementBillForm();
});




