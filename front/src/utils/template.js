import router from "../router/index";
import { EVENT_TYPE } from "./constants";

export const POST_OVERLAY_TEMPLATES = (post) => {
  const postOverlay = document.createElement("div");
  postOverlay.classList.add("speech-bubble");
  postOverlay.innerText = post.content;
  postOverlay.insertAdjacentHTML("afterbegin", `<div class="speech-arrow"></div>`);

  const routePage = () => router.push("posts/" + post.id);
  postOverlay.addEventListener(EVENT_TYPE.CLICK, routePage);

  return postOverlay;
};
