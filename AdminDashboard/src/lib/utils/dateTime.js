/**
 * @param {string | undefined} dateTime
 * @returns {string}
 */
export function getDate(dateTime) {
    if (dateTime !== undefined && dateTime !== "") {
        let dt = new Date(dateTime);
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
