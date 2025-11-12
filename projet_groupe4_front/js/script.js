// Charger le header
fetch("header.html")
  .then((response) => response.text())
  .then((data) => {
    document.getElementById("header-placeholder").innerHTML = data;

    // Une fois le header chargé, on met en avant la page active
    const currentPage = window.location.pathname.split("/").pop().toLowerCase();
    document.querySelectorAll("header .menu a").forEach((link) => {
      const linkPage = link.getAttribute("href").toLowerCase();
      if (currentPage.endsWith(linkPage)) {
        link.classList.add("active");
      }
    });
  });

// Charger le footer
fetch("footer.html")
  .then((response) => response.text())
  .then((data) => {
    document.getElementById("footer-placeholder").innerHTML = data;
  });

// Carouselle 
const carrousel = document.querySelector(".carrousel");
const next = document.querySelector(".next");
const prev = document.querySelector(".prev");

next.addEventListener("click", () => {
  carrousel.scrollBy({ left: 300, behavior: "smooth" });
});

prev.addEventListener("click", () => {
  carrousel.scrollBy({ left: -300, behavior: "smooth" });
});
