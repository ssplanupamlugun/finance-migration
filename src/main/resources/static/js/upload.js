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

        try {

            const response = await fetch("/api/migration/upload", {
                method: "POST",
                body: formData
            });

            if (!response.ok) {
                throw new Error("Migration failed.");
            }

            const result = await response.json();

            console.log(result);

            alert("Migration completed successfully.");

            // TODO:
            // Display the migration summary in a table instead of alert.

        } catch (error) {

            console.error(error);

            alert(error.message);

        } finally {

            uploadSection.style.display = "none";

            submitBtn.disabled = false;

            submitBtn.innerHTML = `
                <i class="bi bi-play-fill me-2"></i>
                Start Migration
            `;
        }

    });

});