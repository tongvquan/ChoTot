let currentSlide = 0;

function showSlide(n) {
  const slides = $(".slide");
  
  if (n >= slides.length) {
    currentSlide = 0;
  } else if (n < 0) {
    currentSlide = slides.length - 1;
  } else {
    currentSlide = n;
  }

  slides.hide();
  slides.eq(currentSlide).show();
}



function changeSlide(n) {
  showSlide(currentSlide + n);
}

$(document).ready(function () {
  showSlide(currentSlide);
});

function sell() {
  window.location.href = "/sell-product";
}

