document.addEventListener("DOMContentLoaded", () => {

    // ============================
    // DOM Elements
    // ============================

    const elements = {
        form: document.getElementById("migrationForm"),
        fileInput: document.getElementById("file"),
        selectedFile: document.getElementById("selectedFile"),
        sessionId: document.getElementById("sessionId"),
        progressSection: document.getElementById("progressSection"),
        submitBtn: document.getElementById("submitBtn")
    };

    // ============================
    // Status Templates
    // ============================

    const STATUS_BADGES = {
        PENDING: `
            <span class="badge text-bg-secondary">
                Pending
            </span>
        `,
        COMPLETED: `
            <span class="badge text-bg-success">
                <i class="bi bi-check-circle-fill me-1"></i>
                Completed
            </span>
        `,
        FAILED: `
            <span class="badge text-bg-danger">
                <i class="bi bi-x-circle-fill me-1"></i>
                Failed
            </span>
        `
    };

    // ============================
    // Event Listeners
    // ============================

    elements.fileInput.addEventListener("change", () => {
        elements.selectedFile.textContent =
            elements.fileInput.files.length > 0
                ? elements.fileInput.files[0].name
                : "No workbook selected";
    });

    elements.form.addEventListener("submit", handleMigration);

    // ============================
    // Migration
    // ============================

    async function handleMigration(event) {

        event.preventDefault();

        const file = elements.fileInput.files[0];

        if (!file) {
            alert("Please select a workbook.");
            return;
        }

        const formData = new FormData();
        formData.append("file", file);
        formData.append("sessionId", elements.sessionId.value);

        setLoading(true);

        try {

            const response = await fetch("/api/migration/upload", {
                method: "POST",
                body: formData
            });

            const result = await response.json();

            if (!response.ok) {
                updateAllSheetStatuses("PENDING");
                alert(result.message || result.error || "Migration failed.");
                console.error(result);
                return;
            }

            if (!result.success) {
                alert(result.message || result.error);
                return;
            }

            result.data.forEach(updateSheetFromResponse);

            alert(result.message);

        } catch (error) {

            updateAllSheetStatuses("PENDING");
            alert("Unable to connect to the server.");
            console.error(error);

        } finally {

            setLoading(false);

        }
    }

    // ============================
    // Helpers
    // ============================

    function setLoading(isLoading) {

        elements.progressSection.style.display = isLoading ? "block" : "none";

        elements.submitBtn.disabled = isLoading;

        elements.submitBtn.innerHTML = isLoading
            ? `
                <span class="spinner-border spinner-border-sm me-2"></span>
                Migrating...
              `
            : `
                <i class="bi bi-play-fill me-2"></i>
                Start Migration
              `;
    }

    function updateSheetFromResponse(sheet) {

        let status = "FAILED";

        if (sheet.isSuccess) {
            status = "COMPLETED";
        } else if (sheet.status?.startsWith("Skipped")) {
            status = "PENDING";
        }

        updateSheetStatus(sheet.sheetName, status, sheet.status);
    }

    function updateAllSheetStatuses(status, message = "") {

        document.querySelectorAll(".sheet-name").forEach(cell => {
            updateSheetStatus(cell.textContent.trim(), status, message);
        });

    }

    function updateSheetStatus(sheetName, status, message = "") {

        const row = document.getElementById(`sheet-${sheetName}`);

        if (!row) return;

        row.querySelector(".status-cell").innerHTML =
            STATUS_BADGES[status] || STATUS_BADGES.PENDING;

        row.querySelector(".message-cell").textContent = message;
    }

});