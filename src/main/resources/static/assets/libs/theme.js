function loadTheme() {
    document.addEventListener("contextmenu", (event) => event.preventDefault());

    let darkTheme = localStorage.getItem("data-bs-theme");
    if (darkTheme === null) darkTheme = "dark";

    darkTheme = darkTheme === "dark";
    document.body.dataset.bsTheme = darkTheme ? "dark" : "light";
}

function toggleTheme() {
    let darkTheme = localStorage.getItem("data-bs-theme");
    if (darkTheme === null) darkTheme = "dark";

    darkTheme = darkTheme === "dark";
    localStorage.setItem("data-bs-theme", darkTheme === true ? "light" : "dark");
    loadTheme();
}

function createToggleButton() {
    const toggleButton = `
<button style="border: 10px; padding: 10px; width: auto; border-radius: 0; box-shadow: 0 0; cursor: pointer; position: absolute; top: 20px; right: 20px;" class="btn" id="toggle" type="button" onclick="toggleTheme()">
    <i style="font-size: 24px" class="fas">&#xf186;</i>
</button>
    `.trim();

    let container = document.body.getElementsByClassName("container")[0];
    container.innerHTML = toggleButton + "\n" + container.innerHTML;
}
