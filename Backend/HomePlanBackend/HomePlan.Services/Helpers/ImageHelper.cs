using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Security.Cryptography.X509Certificates;
using System.Web;
using  System.Drawing;

namespace HomePlan.Services.Helpers
{
    public static class ImageHelper
    {
        /// <summary>
        /// Save a BASE64 encoded image and return the locationn of the image.
        /// </summary>
        /// <param name="userSaving"></param>
        /// <param name="encodedImage"></param>
        /// <returns></returns>
        public static string SaveImage(Entities.User userSaving, string encodedImage)
        {
            var imgDirectory = Path.Combine(Properties.Settings.Default.ImageFiles, userSaving.UserID.ToString());

            if (!Directory.Exists(imgDirectory))
            {
                Directory.CreateDirectory(imgDirectory);
            }

            byte[] bytes = Convert.FromBase64String(encodedImage);

            using (var ms = new MemoryStream(bytes))
            {
                using (Image img = Image.FromStream(ms))
                {
                    Guid imgGuid = Guid.NewGuid();
                    var strFileName = Path.Combine(userSaving.UserID.ToString(),  imgGuid.ToString() + ".jpg");
                    var strFullFile = Path.Combine(Properties.Settings.Default.ImageFiles, strFileName);
                    img.Save(strFullFile);

                    return strFileName;
                }
            }
        }

        public static bool RemoveImage(string imageLocation)
        {
            throw new NotImplementedException();
            //tODO;
        }
    }
}