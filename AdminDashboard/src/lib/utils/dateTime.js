/**
 * @param {string | undefined} dateTime
 * @returns {string}
 */
export function getDate(dateTime) {
    if (dateTime !== undefined && dateTime !== "") {
        let dt = new Date(dateTime);
        console.log(dt);
        return dt.getFullYear() + "-" + dt.getMonth() + "-" + dt.getDate();
    }
    return "-";
}

/**
 * @param {string | undefined} dateTime
 * @returns {string}
 */
export function getTime(dateTime) {
    if (dateTime !== undefined && dateTime !== "") {
        let dt = new Date(dateTime);
        return dt.getHours() + ":" + dt.getMinutes();
    }
    return "-";
}

/**
 * @param {string | undefined} inputDate
 */
export function getFormattedDate(inputDate) {
    let formattedDate = "";
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
            formattedDate = "Not Valid format";
        }
    }
    return formattedDate;
}

/** @param {string | number | Date} date */
export function getFormatDateTime(date) {
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