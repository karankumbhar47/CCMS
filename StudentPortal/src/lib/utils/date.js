/**  @param {string | undefined} inputDate */
export function formatDateTime(inputDate) {
    let formattedDate = "Invalid Date";
    if (inputDate) {
        try {
            const [datePart, timePart] = inputDate.split(" ");
            const [month, day, _] = datePart.split("-");
            const [hour, minute] = timePart.split(":");
            const months = [
                "January",
                "February",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "December",
            ];
            const monthName = months[parseInt(month, 10) - 1];
            const time = `${hour}:${minute}`;
            formattedDate = `${day} ${monthName} ${time}`;
        } catch (error) {
            formattedDate = "";
        }
    } else {
        formattedDate = "";
    }
    return formattedDate;
}

/** @param {string | number | Date} date */
export function getFormatDateTime(date) {
    /** @type {Intl.DateTimeFormatOptions} */
    const options = {
        year: "numeric",
        month: "2-digit",
        day: "2-digit",
        hour: "2-digit",
        minute: "2-digit",
        timeZoneName: "short",
    };

    const dateString = new Date(date).toLocaleString("en-US", options);
    return dateString.replace(/\//g, "-").replace(",", "");
}
