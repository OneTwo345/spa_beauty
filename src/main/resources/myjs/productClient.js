const service = document.getElementsByClassName("service-carousel");
let products = [];

async function getProducts() {
    const res = await fetch('http://192.168.1.24:8080/api/products');
    return await res.json();
}


async function renderCarousel() {
    const pageable = await getProducts();
    products = pageable.content;
    renderServiceCarousel(products);
}

function renderServiceCarousel(items) {
    let str = '';
    items.forEach((e, i) => {
        str += renderItemStr(e, i);
    })
    service.innerHTML = str;
}

function renderItemStr(item, index) {
    return `<div class="service-item position-relative">
            <img class="img-fluid" src="../static/assets/user/img/service-1.jpg" alt="">
            <div class="service-text text-center">
                <h4 class="text-white font-weight-medium px-3">${item.name}</h4>
                <p class="text-white px-3 mb-3">${item.description}</p>
                <p class="text-white px-3 mb-3">${item.price}</p>
                <div class="w-100 bg-white text-center p-4">
                    <a class="btn btn-primary" href="">Make Order</a>
                </div>
            </div>
        </div>`
}