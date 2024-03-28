// DOM
let prev = document.querySelector('.minus-btn');
let next = document.querySelector('.plus-btn');
let inputCounter = document.querySelector('#input_counter');
let btnBuy = document.querySelector('#buy-btn')
let productitem = {
    name : "",
    price : "686000",
    color : {
        images: "",
        text: ""
    },
    sizes : "",
    quantity : ""
}



// function load data
productDetailFunc()
function productDetailFunc(){

    let title = document.querySelector('.title_product')
    let color = document.querySelector('#list_color_tier')
    
    // load data in dom
    title.innerText = productDetails.name

    // load data in product item
    productitem.name = productDetails.name

    let listColor = productDetails.colors.map((item,index)=>{
        if(index == 0){
            return `<a class="color-item color-a-css"  href="javascript:void(0)">
                        <div>
                            <img class="color-product-img" width="75px" height="75px" src="${item.images}" alt="">
                        </div>
                        <div class="text-center">
                            <span class="text-color ">${item.text}</span>
                        </div>
                    </a>`;
        }else{
            return `<a class="color-item "  href="javascript:void(0)">
            <div>
                <img class="color-product-img" width="75px" height="75px" src="${item.images}" alt="">
            </div>
            <div class="text-center">
                <span class="text-color ">${item.text}</span>
            </div>
        </a>`;
        }
    })
    color.innerHTML = listColor.join('')
    // productitem.quantity = inputCounter.value
    changeProductTier()
    changeColorProduct()
}




prev.addEventListener("click", prevCounter);
next.addEventListener("click", nextCounter);
// btnBuy.addEventListener("click", saveProductItem);

function prevCounter(){
    inputCounter.value -= 1;
    productitem.quantity = Number(inputCounter.value);
}
function nextCounter(){
    inputCounter.value = Number(inputCounter.value) + 1;
    productitem.quantity = Number(inputCounter.value);
}
function changeProductTier(){
    let sizeTier = document.querySelectorAll('.box-number');
    sizeTier.forEach(item => {
        item.addEventListener("click", function (){
            let checkClass = item.classList.contains("bg-black")
            if(!checkClass){
                let listBgBlack = document.querySelectorAll('.bg-black');
                listBgBlack[0].classList.remove('bg-black')
                item.classList.add("bg-black")
            }
        })
    });
}


function saveProductItem(){
    localStorage.setItem('listproduct',JSON.stringify(productitem))
}

function changeColorProduct(){
    let colorChange = document.querySelectorAll('.color-item')
    colorChange.forEach(pro => {
        pro.addEventListener("click", function (){
            // productitem.color.images = pro.children[0].children[0].getAttribute('src')
            // productitem.color.text = pro.children[1].children[0].innerText
            let checkColor = pro.classList.contains("color-a-css")
            if(!checkColor){
                let itemColor = document.querySelectorAll('.color-a-css')
                itemColor[0].classList.remove('color-a-css')
                pro.classList.add("color-a-css")
            }
            // console.log(productitem)
        })
    });
}

let listproducts = []
document.querySelector('.buy-btn').onclick = ()=>{
    let objectData = {color:{}}
    let colorChange = document.querySelector('.color-item.color-a-css');
    let SizeChange = document.querySelector('.box-number.bg-black');
    objectData.color.images = colorChange.children[0].children[0].getAttribute('src');
    objectData.color.text = colorChange.children[1].children[0].innerText;
    objectData.color.text = colorChange.children[1].children[0].innerText;
    objectData.sizes = SizeChange.innerText;
    objectData.quantity = Number(document.querySelector('#input_counter').value);
    objectData.quantity = document.querySelector('h3.title_product').innerText;
    objectData.quantity = document.querySelector('h3.title_product').innerText;
    objectData.price = 686000;



    if(listproducts.length <= 0 ){
        listproducts.push(objectData)
        return false;
    }
    // listproducts = listproducts.map((item,index)=>{
    //     if(item.name === productitem.name && item.color.text === productitem.color.text){
    //         item.quantity = item.quantity +  productitem.quantity;
    //     }
    //     return item
    // })
    console.log(listproducts)

}
