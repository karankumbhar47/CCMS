/** @typedef {import("$lib/generated/models/Announcement").Announcement} Announcement*/

/** @description Dummy loading function that returns dummy data
 * @returns {Promise<{announcements: Array.<Announcement>}>} */
export async function load() {
    return {
        announcements: [
            {
                id: 1,
                title: "My First dummy Announcement",
                description:
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lorem dolor sed viverra ipsum. Natoque penatibus et magnis dis. Pellentesque adipiscing commodo elit at imperdiet dui accumsan sit amet. Ornare massa eget egestas purus. Quisque id diam vel quam. Risus sed vulputate odio ut enim blandit. Habitant morbi tristique senectus et netus et malesuada fames. Lectus magna fringilla urna porttitor rhoncus dolor purus. Sed viverra tellus in hac habitasse. Risus nullam eget felis eget nunc. Fringilla urna porttitor rhoncus dolor. Proin sed libero enim sed faucibus turpis in eu. Proin fermentum leo vel orci porta non pulvinar neque laoreet. Magna sit amet purus gravida quis blandit turpis.",
                time: "20-06-2043",
            },
            {
                id: 2,
                title: "My second dummy Announcement",
                description:
                    "Sit amet justo donec enim diam vulputate ut pharetra sit.Lectus magna fringilla urna porttitor rhoncus dolor purus.Sapien pellentesque habitant morbi tristique senectus et.Et netus et malesuada fames ac turpis egestas sed.Nam at lectus urna duis convallis convallis tellus.Lorem mollis aliquam ut porttitor leo a diam sollicitudin tempor.Nulla pellentesque dignissim enim sit amet.Feugiat nisl pretium fusce id velit ut tortor.Consequat semper viverra nam libero justo laoreet.Neque ornare aenean euismod elementum nisi quis eleifend quam.Nisl suscipit adipiscing bibendum est ultricies integer quis.Id aliquet risus feugiat in.Bibendum at varius vel pharetra vel turpis nunc eget lorem.Mollis nunc sed id semper risus in hendrerit gravida rutrum.",
                time: "20-06-2043",
            },
            {
                id: 3,
                title: "My third dummy Announcement",
                description:
                    "Proin nibh nisl condimentum id venenatis a condimentum vitae.Netus et malesuada fames ac.Adipiscing commodo elit at imperdiet dui accumsan sit amet.Sit amet mauris commodo quis imperdiet massa tincidunt nunc pulvinar.Feugiat vivamus at augue eget arcu.Eu feugiat pretium nibh ipsum consequat.Non sodales neque sodales ut etiam sit amet nisl.Purus non enim praesent elementum.Nunc scelerisque viverra mauris in aliquam sem fringilla ut.Dignissim diam quis enim lobortis scelerisque.Lacus luctus accumsan tortor posuere ac ut consequat semper viverra.",
                time: "20-06-2043",
            },
            {
                id: 4,
                title: "Yet another dummy announcement",
                description:
                    "Et netus et malesuada fames ac.Porttitor lacus luctus accumsan tortor posuere ac.Dui id ornare arcu odio ut sem nulla pharetra.Malesuada pellentesque elit eget gravida cum.A diam sollicitudin tempor id eu.Blandit massa enim nec dui nunc mattis enim.Sed vulputate odio ut enim.Vestibulum rhoncus est pellentesque elit ullamcorper dignissim.Tortor posuere ac ut consequat.Tellus orci ac auctor augue mauris augue neque gravida.Diam phasellus vestibulum lorem sed.",
                time: "20-06-2043",
            },
        ],
    };
}
