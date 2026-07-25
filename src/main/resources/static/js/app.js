document.addEventListener("DOMContentLoaded", function () {

    initializeSidebar();

    initializeTooltips();

    autoCloseAlerts();

});

/**
 * Highlight active sidebar menu.
 */
function initializeSidebar() {

    const currentPath = window.location.pathname;

    const menuItems = document.querySelectorAll(".sidebar .list-group-item");

    menuItems.forEach(item => {

        item.classList.remove("active");

        const href = item.getAttribute("href");

        if (href && currentPath === href) {
            item.classList.add("active");
        }

    });

}

/**
 * Initialize Bootstrap tooltips.
 */
function initializeTooltips() {

    const tooltipTriggerList = document.querySelectorAll(
        '[data-bs-toggle="tooltip"]'
    );

    [...tooltipTriggerList].map(
        element => new bootstrap.Tooltip(element)
    );

}

/**
 * Automatically close alerts after 5 seconds.
 */
function autoCloseAlerts() {

    const alerts = document.querySelectorAll(".alert");

    alerts.forEach(alert => {

        setTimeout(() => {

            const bsAlert = bootstrap.Alert.getOrCreateInstance(alert);

            bsAlert.close();

        }, 5000);

    });

}

/**
 * Show loading spinner on button.
 */
function showLoading(button, text = "Processing...") {

    button.disabled = true;

    button.innerHTML =
        '<span class="spinner-border spinner-border-sm me-2"></span>' + text;

}

/**
 * Restore button.
 */
function hideLoading(button, text) {

    button.disabled = false;

    button.innerHTML = text;

}