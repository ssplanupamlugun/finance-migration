document.addEventListener("DOMContentLoaded", () => {

    const fileInput = document.getElementById("file");
    const selectedFile = document.getElementById("selectedFile");

    const form = document.getElementById("migrationForm");

    const uploadSection = document.getElementById("uploadSection");
    const submitBtn = document.getElementById("submitBtn");

    fileInput.addEventListener("change", () => {

        if (fileInput.files.length > 0) {
            selectedFile.textContent = fileInput.files[0].name;
        } else {
            selectedFile.textContent = "No workbook selected";
        }

    });

    form.addEventListener("submit", async (e) => {

        e.preventDefault();

        const file = fileInput.files[0];
        const sessionId = document.getElementById("sessionId").value;

        if (!file) {
            alert("Please select a workbook.");
            return;
        }

        uploadSection.style.display = "block";

        submitBtn.disabled = true;

        submitBtn.innerHTML = `
            <span class="spinner-border spinner-border-sm me-2"></span>
            Migrating...
        `;

        const formData = new FormData();

        formData.append("file", file);
        formData.append("sessionId", sessionId);
        document.querySelectorAll(".sheet-name").forEach(cell => {
            updateSheetStatus(cell.textContent.trim(), "PROCESSING");
        });

        try {

            const response = await fetch("/api/migration/upload", {
                method: "POST",
                body: formData
            });

            const result = await response.json();

            if (result.success) {

                updateSheetStatus(
                    result.data.sheetName,
                    result.data.isSuccess ? "COMPLETED" : "FAILED",
                    result.data.status
                );

                alert(result.message);
                console.log(result.data);

            } else {

                updateSheetStatus(
                    selectedSheetName,
                    "FAILED",
                    result.error ?? result.message
                );

                alert(result.error ?? result.message);
            }

        } catch (err) {

            updateSheetStatus(
                selectedSheetName,
                "FAILED",
                "Unable to connect to the server."
            );

            alert("Unable to connect to the server.");
            console.error(err);

        } finally {

            uploadSection.style.display = "none";

            submitBtn.disabled = false;

            submitBtn.innerHTML = `
        <i class="bi bi-play-fill me-2"></i>
        Start Migration
    `;
        }

    });

    function updateSheetStatus(sheetName, status, message = "") {

        const row = document.getElementById(`sheet-${sheetName}`);

        if (!row) return;

        const statusCell = row.querySelector(".status-cell");
        const messageCell = row.querySelector(".message-cell");

        let statusHtml = "";

        switch (status) {

            case "PROCESSING":
                statusHtml = `
                <span class="badge text-bg-primary">
                    <span class="spinner-border spinner-border-sm me-1"></span>
                    Processing
                </span>
            `;
                break;

            case "COMPLETED":
                statusHtml = `
                <span class="badge text-bg-success">
                    <i class="bi bi-check-circle-fill me-1"></i>
                    Completed
                </span>
            `;
                break;

            case "FAILED":
                statusHtml = `
                <span class="badge text-bg-danger">
                    <i class="bi bi-x-circle-fill me-1"></i>
                    Failed
                </span>
            `;
                break;

            default:
                statusHtml = `
                <span class="badge text-bg-secondary">
                    Pending
                </span>
            `;
        }

        statusCell.innerHTML = statusHtml;
        messageCell.textContent = message;
    }

});