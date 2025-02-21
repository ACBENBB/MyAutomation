function updateBaseline(testName, snapshotName, fullPath, snapshotPngAsBase64) {
    if (confirm(`Are you sure you want to update snapshot of ${testName} - ${snapshotName} in S3?`)) {
        let url = "[s3fileupdaterurl]api/s3Screenshot/";

        $.ajax({
          url:url,
          type:"POST",
          data:JSON.stringify({ "path": fullPath, "base64Content": snapshotPngAsBase64 }),
          contentType:"application/json; charset=utf-8",
          dataType:"text"
        })
          .done(function(data, textStatus, jqXHR) {
            alert("Action successful!");
          })
           .fail(function(jqXHR, status, error) {
            alert(`An error has occur: status code:${jqXHR.status}. Details: ${status} ${error}`);
         });
    }
}