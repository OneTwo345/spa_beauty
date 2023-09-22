const service = document.getElementById("comboShow");
let productsClient = [];

async function getProducts() {
    const res = await fetch('http://localhost:8080/api/combos/list');
    return await res.json();
}


async function renderCarousel() {
    const list = await getProducts();
    productsClient = list
    renderServiceCarousel(list);
}

function renderServiceCarousel(items) {
    let str = '';
    items.forEach((e, i) => {
        str += renderItemStr(e, i);
    });
    service.innerHTML = str;

    if (items.length > 1) {
        $(".service-carousel").owlCarousel({
            autoplay: true,
            smartSpeed: 1500,
            loop: true,
            dots: false,
            nav: false,
            items: 5,
            responsive: {
                0: {
                    items: 1
                },
                600: {
                    items: 3
                },
                1000: {
                    items: 5
                }
            }
        });
    }
}

function renderItemStr(item, index) {
    return `  <div class="bg-white">
                                <div class="d-flex align-items-center justify-content-between border-bottom border-primary p-4">
                                    <h1 class="display-4 mb-0">
                                        <small class="align-top text-muted font-weight-medium" style="font-size: 22px; line-height: 45px;">$</small>49<small class="align-bottom text-muted font-weight-medium" style="font-size: 16px; line-height: 40px;">/Mo</small>
                                    </h1>
                                    <h5 class="text-primary text-uppercase m-0">${item.name}</h5>
                                </div>
                                <div class="p-4">
                                    <p><i class="fa fa-check text-success mr-2"></i>Full Body Massage</p>
<!--                                    <p><i class="fa fa-check text-success mr-2"></i>Deep Tissue Massage</p>-->
<!--                                    <p><i class="fa fa-check text-success mr-2"></i>Hot Stone Massage</p>-->
<!--                                    <p><i class="fa fa-check text-success mr-2"></i>Tissue Body Polish</p>-->
<!--                                    <p><i class="fa fa-check text-success mr-2"></i>Foot & Nail Care</p>-->
                                    <a href="" class="btn btn-primary my-2">Order Now</a>
                                </div>
                            </div>`
}

window.onload = async () => {
    await renderCarousel()
}